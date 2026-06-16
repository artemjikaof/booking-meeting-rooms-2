package com.example.roombooking.data.repository

import com.example.roombooking.data.local.TokenManager
import com.example.roombooking.data.remote.YandexAuthApi
import com.example.roombooking.data.remote.YandexCalendarApi
import com.example.roombooking.data.remote.YandexEventDto
import com.example.roombooking.util.YandexConfig
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class YandexCalendarRepository @Inject constructor(
    private val authApi: YandexAuthApi,
    private val calendarApi: YandexCalendarApi,
    private val tokenManager: TokenManager,
    private val httpClient: OkHttpClient
) {
    private val icsDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")

    // ─── Авторизация ───────────────────────────────────────────────────────

    suspend fun handleAuthCode(code: String): Result<Unit> {
        return try {
            val response = authApi.getTokens(
                code = code,
                clientId = YandexConfig.CLIENT_ID,
                clientSecret = YandexConfig.CLIENT_SECRET
            )
            tokenManager.saveTokens(response.accessToken, response.refreshToken)
            ensureLogin(response.accessToken)
            Result.success(Unit)
        } catch (e: Exception) {
            android.util.Log.e("YandexRepo", "Auth failed", e)
            Result.failure(e)
        }
    }

    fun isAuthorized(): Boolean = tokenManager.getAccessToken() != null

    // Получаем и кешируем логин пользователя — он нужен для CalDAV URL
    private suspend fun ensureLogin(token: String): Pair<String, String> {
        val login = tokenManager.getUserLogin()
        val userId = tokenManager.getUserId()
        if (login != null && userId != null) return login to userId

        val info = authApi.getUserInfo("OAuth $token")
        // CalDAV требует полный email: vasya@yandex.ru
        val fullLogin = if (info.login.contains("@")) info.login else "${info.login}@yandex.ru"
        tokenManager.saveUserLogin(fullLogin, info.id)
        android.util.Log.d("YandexRepo", "User login: $fullLogin, id: ${info.id}")
        return fullLogin to info.id
    }

    // ─── Запись события в Яндекс Календарь ────────────────────────────────

    suspend fun syncBookingToYandex(
        summary: String,
        description: String?,
        start: String,  // ISO-8601 со смещением, напр. "2025-06-01T10:00:00+03:00"
        end: String,
        location: String,
        externalId: String
    ): Result<String> {
        val token = tokenManager.getAccessToken()
            ?: return Result.failure(Exception("Not authorized"))

        return try {
            val (login, _) = ensureLogin(token)

            // ИСПРАВЛЕНО: URL использует login (email), а не uid:userId
            val url = "https://caldav.yandex.ru/calendars/$login/events-default/$externalId.ics"
            android.util.Log.d("YandexRepo", "CalDAV PUT: $url")

            val ics = generateIcs(summary, description, start, end, location, externalId)
            val requestBody = ics.toRequestBody("text/calendar; charset=utf-8".toMediaTypeOrNull())

            val response = calendarApi.createOrUpdateEvent(url, "OAuth $token", body = requestBody)

            if (response.isSuccessful || response.code() == 201) {
                android.util.Log.i("YandexRepo", "Synced event $externalId to Yandex")
                Result.success(externalId)
            } else {
                val err = response.errorBody()?.string()
                android.util.Log.e("YandexRepo", "CalDAV PUT error ${response.code()}: $err")
                Result.failure(Exception("CalDAV Error: ${response.code()} — $err"))
            }
        } catch (e: Exception) {
            android.util.Log.e("YandexRepo", "syncBookingToYandex failed", e)
            Result.failure(e)
        }
    }

    // ─── Чтение событий из Яндекс Календаря ───────────────────────────────

    // ИСПРАВЛЕНО: Яндекс не имеет REST API для событий — используем CalDAV REPORT
    suspend fun getYandexEvents(): Result<List<CalendarEventData>> {
        val token = tokenManager.getAccessToken()
            ?: return Result.failure(Exception("Not authorized"))

        // ИСПРАВЛЕНО: withContext(Dispatchers.IO) — httpClient.execute() блокирующий,
        // нельзя вызывать на главном потоке даже внутри suspend функции
        return kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
            try {
                val (login, _) = ensureLogin(token)
                val url = "https://caldav.yandex.ru/calendars/$login/events-default/"

                val reportXml = """<?xml version="1.0" encoding="UTF-8"?>
<C:calendar-query xmlns:D="DAV:" xmlns:C="urn:ietf:params:xml:ns:caldav">
  <D:prop>
    <D:getetag/>
    <C:calendar-data/>
  </D:prop>
  <C:filter>
    <C:comp-filter name="VCALENDAR">
      <C:comp-filter name="VEVENT"/>
    </C:comp-filter>
  </C:filter>
</C:calendar-query>"""

                val request = Request.Builder()
                    .url(url)
                    .method("REPORT", reportXml.toRequestBody("application/xml; charset=utf-8".toMediaTypeOrNull()))
                    .header("Authorization", "OAuth $token")
                    .header("Depth", "1")
                    .build()

                val response = httpClient.newCall(request).execute()
                val body = response.body?.string() ?: ""

                if (response.isSuccessful) {
                    val events = parseCalDavResponse(body)
                    android.util.Log.i("YandexRepo", "Fetched ${events.size} events from Yandex")
                    Result.success(events)
                } else {
                    android.util.Log.e("YandexRepo", "CalDAV REPORT failed ${response.code}: $body")
                    Result.failure(Exception("CalDAV REPORT failed: ${response.code}"))
                }
            } catch (e: Exception) {
                android.util.Log.e("YandexRepo", "getYandexEvents failed", e)
                Result.failure(e)
            }
        }
    }

    // ─── Обновление / удаление ─────────────────────────────────────────────

    suspend fun updateYandexEvent(
        yandexEventId: String,
        summary: String,
        description: String?,
        start: String,
        end: String,
        location: String,
        externalId: String
    ): Result<Unit> = syncBookingToYandex(summary, description, start, end, location, externalId).map {}

    suspend fun deleteYandexEvent(yandexEventId: String): Result<Unit> {
        val token = tokenManager.getAccessToken()
            ?: return Result.failure(Exception("Not authorized"))
        return try {
            val (login, _) = ensureLogin(token)
            val url = "https://caldav.yandex.ru/calendars/$login/events-default/$yandexEventId.ics"
            val response = calendarApi.deleteEvent(url, "OAuth $token")
            if (response.isSuccessful || response.code() == 404) Result.success(Unit)
            else Result.failure(Exception("Delete failed: ${response.code()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ─── Генерация ICS ────────────────────────────────────────────────────

    // ИСПРАВЛЕНО: buildString без отступов — CalDAV требует строки с позиции 0
    private fun generateIcs(
        summary: String, description: String?,
        start: String, end: String,
        location: String, externalId: String
    ): String {
        val startUtc = OffsetDateTime.parse(start).atZoneSameInstant(ZoneOffset.UTC)
        val endUtc = OffsetDateTime.parse(end).atZoneSameInstant(ZoneOffset.UTC)
        val now = OffsetDateTime.now(ZoneOffset.UTC).format(icsDateFormatter)

        // Экранирование спецсимволов ICS
        fun escape(s: String) = s
            .replace("\\", "\\\\")
            .replace(";", "\\;")
            .replace(",", "\\,")
            .replace("\n", "\\n")

        return buildString {
            append("BEGIN:VCALENDAR\r\n")
            append("VERSION:2.0\r\n")
            append("PRODID:-//RoomBooking//EN\r\n")
            append("CALSCALE:GREGORIAN\r\n")
            append("BEGIN:VEVENT\r\n")
            append("UID:event-$externalId@roombooking\r\n")
            append("DTSTAMP:$now\r\n")
            append("DTSTART:${startUtc.format(icsDateFormatter)}\r\n")
            append("DTEND:${endUtc.format(icsDateFormatter)}\r\n")
            append("SUMMARY:${escape(summary)}\r\n")
            append("DESCRIPTION:${escape(description ?: "")}\r\n")
            append("LOCATION:${escape(location)}\r\n")
            append("END:VEVENT\r\n")
            append("END:VCALENDAR\r\n")
        }
    }

    // ─── Парсинг CalDAV REPORT ответа ─────────────────────────────────────

    private fun parseCalDavResponse(xml: String): List<CalendarEventData> {
        val events = mutableListOf<CalendarEventData>()
        // Каждый <calendar-data> содержит VCALENDAR блок
        val vcalRegex = Regex("BEGIN:VCALENDAR.*?END:VCALENDAR", RegexOption.DOT_MATCHES_ALL)
        vcalRegex.findAll(xml).forEach { match ->
            try {
                val ics = match.value
                val uid = extractIcsField(ics, "UID") ?: return@forEach
                val summary = extractIcsField(ics, "SUMMARY") ?: ""
                val description = extractIcsField(ics, "DESCRIPTION") ?: ""
                val location = extractIcsField(ics, "LOCATION") ?: ""
                val dtStart = parseIcsDate(extractIcsField(ics, "DTSTART")) ?: return@forEach
                val dtEnd = parseIcsDate(extractIcsField(ics, "DTEND")) ?: dtStart + 3600_000
                events.add(
                    CalendarEventData(
                        id = uid.hashCode().toLong(),
                        title = summary,
                        description = description,
                        location = location,
                        dtStart = dtStart,
                        dtEnd = dtEnd
                    )
                )
            } catch (e: Exception) {
                android.util.Log.w("YandexRepo", "Failed to parse VEVENT", e)
            }
        }
        return events
    }

    private fun extractIcsField(ics: String, field: String): String? =
        Regex("^$field[^:]*:(.+)$", RegexOption.MULTILINE)
            .find(ics)?.groupValues?.get(1)?.trim()

    private fun parseIcsDate(dateStr: String?): Long? {
        dateStr ?: return null
        return try {
            // Нормализуем форматы: 20250601T100000Z → 2025-06-01T10:00:00+00:00
            val normalized = dateStr
                .replace(Regex("(\\d{4})(\\d{2})(\\d{2})T(\\d{2})(\\d{2})(\\d{2})Z"),
                    "$1-$2-$3T$4:$5:$6+00:00")
                .replace(Regex("(\\d{4})(\\d{2})(\\d{2})T(\\d{2})(\\d{2})(\\d{2})"),
                    "$1-$2-$3T$4:$5:$6")
                .let { if (!it.contains("+") && !it.endsWith("Z")) "${it}+00:00" else it }
            OffsetDateTime.parse(normalized).toInstant().toEpochMilli()
        } catch (e: Exception) {
            android.util.Log.w("YandexRepo", "Cannot parse ICS date: $dateStr", e)
            null
        }
    }
}