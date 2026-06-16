package com.example.roombooking.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncPreferences @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs = context.getSharedPreferences("sync_prefs", Context.MODE_PRIVATE)

    var syncEnabled: Boolean
        get() = prefs.getBoolean("sync_enabled", false)
        set(value) = prefs.edit().putBoolean("sync_enabled", value).apply()

    var selectedCalendarId: Long?
        get() = if (prefs.contains("calendar_id")) prefs.getLong("calendar_id", -1) else null
        set(value) = if (value != null) prefs.edit().putLong("calendar_id", value).apply()
        else prefs.edit().remove("calendar_id").apply()

    var filterTags: List<String>
        get() = prefs.getString("filter_tags", "#Бронирование,#Помещение")
            ?.split(",")?.filter { it.isNotBlank() } ?: listOf("#Бронирование", "#Помещение")
        set(value) = prefs.edit().putString("filter_tags", value.joinToString(",")).apply()

    var lastSyncTime: Long
        get() = prefs.getLong("last_sync", 0L)
        set(value) = prefs.edit().putLong("last_sync", value).apply()

    var backgroundSyncEnabled: Boolean
        get() = prefs.getBoolean("bg_sync", true)
        set(value) = prefs.edit().putBoolean("bg_sync", value).apply()

    var backgroundSyncIntervalHours: Int
        get() = prefs.getInt("bg_sync_interval", 4)
        set(value) = prefs.edit().putInt("bg_sync_interval", value).apply()

    // Пароль приложения Яндекс для CalDAV (Basic Auth)
    var yandexAppPassword: String?
        get() = prefs.getString("yandex_app_password", null)
        set(value) = prefs.edit().putString("yandex_app_password", value).apply()

    // Логин Яндекс (кешируем чтобы не зависеть от TokenManager)
    var yandexLogin: String?
        get() = prefs.getString("yandex_login", null)
        set(value) = prefs.edit().putString("yandex_login", value).apply()
}