package com.example.roombooking.data.repository

import com.example.roombooking.data.local.TokenManager
import com.example.roombooking.data.remote.*
import com.example.roombooking.util.YandexConfig
import okhttp3.Credentials
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class YandexCalendarRepository @Inject constructor(
    private val authApi: YandexAuthApi,
    private val calendarApi: YandexCalendarApi,
    private val tokenManager: TokenManager
) {
    private val icsDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")

    suspend fun handleAuthCode(code: String): Result<Unit> {
        return try {
            val response = authApi.getTokens(
                code = code,
                clientId = YandexConfig.CLIENT_ID,
                clientSecret = YandexConfig.CLIENT_SECRET
            )
            tokenManager.saveTokens(response.accessToken, response.refreshToken)
            // Предварительно получаем логин, он нужен для CalDAV URL
            ensureLogin(response.accessToken)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun ensureLogin(token: String): Pair<String, String> {
        val login = tokenManager.getUserLogin()
        val userId = tokenManager.getUserId()
        if (login != null && userId != null) return login to userId
        
        android.util.Log.d("YandexRepo", "Fetching fresh user info...")
        val info = authApi.getUserInfo("OAuth $token")
        tokenManager.saveUserLogin(info.login, info.id)
        return info.login to info.id
    }

    fun isAuthorized(): Boolean = tokenManager.getAccessToken() != null

    suspend fun syncBookingToYandex(
        summary: String,
        description: String?,
        start: String,
        end: String,
        location: String,
        externalId: String
    ): Result<String> {
        android.util.Log.d("YandexRepo", "Starting sync for event $externalId: $summary")
        val token = tokenManager.getAccessToken() ?: run {
            android.util.Log.e("YandexRepo", "Sync failed: No access token")
            return Result.failure(Exception("Not authorized"))
        }
        return try {
            val (login, userId) = ensureLogin(token)
            // Яндекс часто требует именно такой формат URL для OAuth
            val url = "https://caldav.yandex.ru/calendars/uid:$userId/events-default/$externalId.ics"
            
            android.util.Log.d("YandexRepo", "CalDAV PUT: $url")
            val ics = generateIcs(summary, description, start, end, location, externalId)
            
            val requestBody = ics.toRequestBody("text/calendar; charset=utf-8".toMediaTypeOrNull())
            
            var response = calendarApi.createOrUpdateEvent(url, "OAuth $token", body = requestBody)
            if (response.code() == 401) {
                val fullLogin = if (login.contains("@")) login else "$login@yandex.ru"
                val authHeader = Credentials.basic(fullLogin, token)
                response = calendarApi.createOrUpdateEvent(url, authHeader, body = requestBody)
            }
            
            if (response.isSuccessful) {
                android.util.Log.i("YandexRepo", "Successfully synced event $externalId to Yandex")
                Result.success(externalId)
            } else {
                val errorBody = response.errorBody()?.string()
                android.util.Log.e("YandexRepo", "CalDAV Error: ${response.code()} - $errorBody")
                Result.failure(Exception("CalDAV Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            android.util.Log.e("YandexRepo", "Sync failed for event $externalId", e)
            Result.failure(e)
        }
    }

    private fun generateIcs(summary: String, description: String?, start: String, end: String, location: String, externalId: String): String {
        val startUtc = try {
            OffsetDateTime.parse(start).atZoneSameInstant(java.time.ZoneOffset.UTC)
        } catch (e: Exception) {
            android.util.Log.e("YandexRepo", "Error parsing start date: $start", e)
            throw e
        }
        val endUtc = try {
            OffsetDateTime.parse(end).atZoneSameInstant(java.time.ZoneOffset.UTC)
        } catch (e: Exception) {
            android.util.Log.e("YandexRepo", "Error parsing end date: $end", e)
            throw e
        }
        
        val now = OffsetDateTime.now(java.time.ZoneOffset.UTC).format(icsDateFormatter)
        
        // Экранирование для ICS (минимальное)
        val safeSummary = summary.replace(",", "\\,").replace(";", "\\;")
        val safeDescription = (description ?: "").replace(",", "\\,").replace(";", "\\;").replace("\n", "\\n")
        val safeLocation = location.replace(",", "\\,").replace(";", "\\;")

        return """
            BEGIN:VCALENDAR
            VERSION:2.0
            PRODID:-//RoomBooking//EN
            CALSCALE:GREGORIAN
            BEGIN:VEVENT
            UID:event-$externalId@roombooking
            DTSTAMP:$now
            DTSTART:${startUtc.format(icsDateFormatter)}
            DTEND:${endUtc.format(icsDateFormatter)}
            SUMMARY:$safeSummary
            DESCRIPTION:$safeDescription
            LOCATION:$safeLocation
            END:VEVENT
            END:VCALENDAR
        """.trimIndent().replace("\n", "\r\n") // ICS требует CRLF
    }

    suspend fun getYandexEvents(from: String = "", to: String = ""): Result<List<YandexEventDto>> {
        android.util.Log.d("YandexRepo", "Fetching events from Yandex via REST API...")
        val token = tokenManager.getAccessToken() ?: return Result.failure(Exception("Not authorized"))
        return try {
            val response = calendarApi.getEventsRest("OAuth $token")
            if (response.isSuccessful) {
                val events = response.body()?.events ?: emptyList()
                android.util.Log.i("YandexRepo", "Successfully fetched ${events.size} events via REST")
                Result.success(events)
            } else {
                android.util.Log.e("YandexRepo", "REST API Error: ${response.code()} - ${response.errorBody()?.string()}")
                Result.failure(Exception("Fetch failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            android.util.Log.e("YandexRepo", "Error fetching Yandex events via REST", e)
            Result.failure(e)
        }
    }



    suspend fun updateYandexEvent(
        yandexEventId: String,
        summary: String,
        description: String?,
        start: String,
        end: String,
        location: String,
        externalId: String
    ): Result<Unit> {
        return syncBookingToYandex(summary, description, start, end, location, externalId).map { Unit }
    }

    suspend fun deleteYandexEvent(yandexEventId: String): Result<Unit> {
        val token = tokenManager.getAccessToken() ?: return Result.failure(Exception("Not authorized"))
        return try {
            val (login, userId) = ensureLogin(token)
            val url = "https://caldav.yandex.ru/calendars/uid:$userId/events-default/$yandexEventId.ics"
            var response = calendarApi.deleteEvent(url, "OAuth $token")
            if (response.code() == 401) {
                val authHeader = Credentials.basic(login, token)
                response = calendarApi.deleteEvent(url, authHeader)
            }
            if (response.isSuccessful || response.code() == 404) Result.success(Unit)
            else Result.failure(Exception("Delete failed: ${response.code()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


