package com.example.roombooking.di

import com.example.roombooking.data.remote.YandexAuthApi
import com.example.roombooking.data.remote.YandexCalendarApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://caldav.yandex.ru/") // Базовый URL для CalDAV
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideYandexCalendarApi(retrofit: Retrofit): YandexCalendarApi {
        return retrofit.create(YandexCalendarApi::class.java)
    }

    @Provides
    @Singleton
    fun provideYandexAuthApi(): YandexAuthApi {
        // OAuth API использует другой базовый URL, поэтому создаем отдельный инстанс или используем полный путь в методах
        return Retrofit.Builder()
            .baseUrl("https://oauth.yandex.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(YandexAuthApi::class.java)
    }
}
