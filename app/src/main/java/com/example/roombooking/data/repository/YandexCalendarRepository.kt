package com.example.roombooking.data.repository

import android.util.Base64
import com.example.roombooking.data.local.TokenManager
import com.example.roombooking.data.remote.YandexAuthApi
import com.example.roombooking.data.remote.YandexCalendarApi
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
    private val syncPrefs: SyncPreferences,
    private val httpClient: OkHttpClient
) {
    private val icsDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")

    // ─── Авторизация ───────────────────────────────────────────────────────

    suspend fun handleAuthCode(code: String): Result<Unit> {
        return try {
            val response = authApi.getTokens(
                grantType = "authorization_code",
                code = code,
                clientId = YandexConfig.CLIENT_ID,
                clientSecret = YandexConfig.CLIENT_SECRET,
                redirectUri = YandexConfig.REDIRECT_URI
            )
            tokenManager.saveTokens(response.accessToken, response.refreshToken)
            ensureLogin(response.accessToken)
            Result.success(Unit)
        } catch (e: Exception) {
            android.util.Log.e("YandexRepo", "Auth failed", e)
            Result.failure(e)
        }
    }

    fun isAuthorized(): Boolean {
        // Авторизован если есть логин И пароль приложения
        val login = syncPrefs.yandexLogin ?: tokenManager.getUserLogin()
        val password = syncPrefs.yandexAppPassword
        return login != null && password != null
    }

    fun clearAuth() {
        tokenManager.clearTokens()
        syncPrefs.yandexAppPassword = null
        syncPrefs.yandexLogin = null
    }

    // Сохраняем логин и в SyncPrefs тоже — для CalDAV без токена
    private suspend fun ensureLogin(token: String): Pair<String, String> {
        val login = tokenManager.getUserLogin()
        val userId = tokenManager.getUserId()
        if (login != null && userId != null) {
            syncPrefs.yandexLogin = login  // синхронизируем в SyncPrefs
            return login to userId
        }

        val info = authApi.getUserInfo("OAuth $token")
        val fullLogin = if (info.login.contains("@")) info.login else "${info.login}@yandex.ru"
        tokenManager.saveUserLogin(fullLogin, info.id)
        syncPrefs.yandexLogin = fullLogin  // сохраняем в SyncPrefs для CalDAV
        android.util.Log.d("YandexRepo", "User login: $fullLogin, id: ${info.id}")
        return fullLogin to info.id
    }

    // Вызывается из SettingsViewModel когда пользователь вводит пароль приложения
    fun saveAppPassword(password: String) {
        syncPrefs.yandexAppPassword = password
        android.util.Log.d("YandexRepo", "App password saved")
    }

    // ─── Basic Auth header ────────────────────────────────────────────────

    private fun basicAuthHeader(login: String, password: String): String {
        val credentials = Base64.encodeToString(
            "$login:$password".toByteArray(Charsets.UTF_8),
            Base64.NO_WRAP
        )
        return "Basic $credentials"
    }

    // ─── Чтение событий из Яндекс Календаря ───────────────────────────────

    suspend fun getYandexEvents(): Result<List<CalendarEventData>> {
        val login = syncPrefs.yandexLogin ?: tokenManager.getUserLogin()
        ?: return Result.failure(Exception("Не авторизован"))
        val appPassword = syncPrefs.yandexAppPassword
            ?: return Result.failure(Exception("Пароль приложения не задан"))

        return kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
            try {
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
                    .header("Authorization", basicAuthHeader(login, appPassword))
                    .header("Depth", "1")
                    .build()

                val response = httpClient.newCall(request).execute()
                val responseCode = response.code
                val body = response.use { it.body?.string() ?: "" }

                android.util.Log.d("YandexRepo", "CalDAV REPORT $responseCode, body length: ${body.length}")

                if (responseCode in 200..299) {
                    val events = parseCalDavResponse(body)
                    android.util.Log.i("YandexRepo", "Fetched ${events.size} events from Yandex")
                    Result.success(events)
                } else {
                    android.util.Log.e("YandexRepo", "CalDAV REPORT failed $responseCode: $body")
                    Result.failure(Exception("CalDAV REPORT failed: $responseCode"))
                }
            } catch (e: Exception) {
                android.util.Log.e("YandexRepo", "getYandexEvents failed", e)
                Result.failure(e)
            }
        }
    }

    // ─── Запись события в Яндекс Календарь ────────────────────────────────

    suspend fun syncBookingToYandex(
        summary: String,
        description: String?,
        start: String,
        end: String,
        location: String,
        externalId: String
    ): Result<String> {
        val login = syncPrefs.yandexLogin ?: tokenManager.getUserLogin()
        ?: return Result.failure(Exception("Не авторизован"))
        val appPassword = syncPrefs.yandexAppPassword
            ?: return Result.failure(Exception("Пароль приложения не задан"))

        return kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
            try {
                val url = "https://caldav.yandex.ru/calendars/$login/events-default/$externalId.ics"
                android.util.Log.d("YandexRepo", "CalDAV PUT: $url")

                val ics = generateIcs(summary, description, start, end, location, externalId)

                val request = Request.Builder()
                    .url(url)
                    .put(ics.toRequestBody("text/calendar; charset=utf-8".toMediaTypeOrNull()))
                    .header("Authorization", basicAuthHeader(login, appPassword))
                    .build()

                val response = httpClient.newCall(request).execute()
                val responseCode = response.code
                response.body?.close()

                if (responseCode in 200..299 || responseCode == 201) {
                    android.util.Log.i("YandexRepo", "Synced event $externalId to Yandex")
                    Result.success(externalId)
                } else {
                    Result.failure(Exception("CalDAV PUT failed: $responseCode"))
                }
            } catch (e: Exception) {
                android.util.Log.e("YandexRepo", "syncBookingToYandex failed", e)
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
        val login = syncPrefs.yandexLogin ?: tokenManager.getUserLogin()
        ?: return Result.failure(Exception("Не авторизован"))
        val appPassword = syncPrefs.yandexAppPassword
            ?: return Result.failure(Exception("Пароль приложения не задан"))

        return kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
            try {
                val url = "https://caldav.yandex.ru/calendars/$login/events-default/$yandexEventId.ics"
                val request = Request.Builder()
                    .url(url)
                    .delete()
                    .header("Authorization", basicAuthHeader(login, appPassword))
                    .build()

                val response = httpClient.newCall(request).execute()
                val responseCode = response.code
                response.body?.close()

                if (responseCode in 200..299 || responseCode == 404) Result.success(Unit)
                else Result.failure(Exception("Delete failed: $responseCode"))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // ─── Генерация ICS ────────────────────────────────────────────────────

    private fun generateIcs(
        summary: String, description: String?,
        start: String, end: String,
        location: String, externalId: String
    ): String {
        val startUtc = OffsetDateTime.parse(start).atZoneSameInstant(ZoneOffset.UTC)
        val endUtc = OffsetDateTime.parse(end).atZoneSameInstant(ZoneOffset.UTC)
        val now = OffsetDateTime.now(ZoneOffset.UTC).format(icsDateFormatter)

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