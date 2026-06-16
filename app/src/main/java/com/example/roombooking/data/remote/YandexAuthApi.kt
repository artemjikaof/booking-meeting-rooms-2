package com.example.roombooking.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.http.*

interface YandexAuthApi {

    @FormUrlEncoded
    @POST("https://oauth.yandex.ru/token")
    suspend fun getTokens(
        @Field("grant_type") grantType: String,  // убрать default value
        @Field("code") code: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("redirect_uri") redirectUri: String   // добавить redirect_uri
    ): TokenResponse

    @GET("https://login.yandex.ru/info?format=json")
    suspend fun getUserInfo(
        @Header("Authorization") token: String
    ): YandexUserInfoResponse

    @FormUrlEncoded
    @POST("https://oauth.yandex.ru/token")
    suspend fun refreshToken(
        @Field("grant_type") grantType: String,  // убрать default value
        @Field("refresh_token") refreshToken: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): TokenResponse
}

data class YandexUserInfoResponse(
    val login: String,
    val id: String,
    @SerializedName("default_email") val email: String?
)

data class TokenResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String?,
    @SerializedName("expires_in") val expiresIn: Int
)