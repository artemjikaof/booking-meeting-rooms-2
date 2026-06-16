package com.example.roombooking.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.http.*

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface YandexCalendarApi {

    // --- CalDAV (для обратной совместимости или спец. нужд) ---
    @PUT
    suspend fun createOrUpdateEvent(
        @Url url: String,
        @Header("Authorization") token: String,
        @Body body: RequestBody
    ): Response<Unit>

    @DELETE
    suspend fun deleteEvent(
        @Url url: String,
        @Header("Authorization") token: String
    ): Response<Unit>

    @GET
    suspend fun getEventsCalDav(
        @Url url: String,
        @Header("Authorization") token: String
    ): Response<ResponseBody>

    @HTTP(method = "REPORT", hasBody = true)
    suspend fun reportEvents(
        @Url url: String,
        @Header("Authorization") token: String,
        @Body body: RequestBody
    ): Response<ResponseBody>

    // --- REST API (Рекомендуемый способ для OAuth) ---
    @GET("https://api.calendar.yandex.net/v1/events")
    suspend fun getEventsRest(
        @Header("Authorization") token: String
    ): Response<YandexRestResponse>
}

data class YandexRestResponse(
    val events: List<YandexEventDto>
)

data class YandexEventDto(
    val id: String?,
    val summary: String,
    val description: String?,
    val start: YandexTimeDto,
    val end: YandexTimeDto,
    val location: String?
)

data class YandexTimeDto(
    val dateTime: String
)

