package com.example.roombooking.presentation.settings

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.example.roombooking.data.repository.*
import com.example.roombooking.util.CalendarSyncWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val syncPrefs: SyncPreferences,
    private val calendarSyncManager: CalendarSyncManager,
    private val eventRepository: EventRepository,
    private val yandexRepository: YandexCalendarRepository
) : ViewModel() {

    private val _syncEnabled = MutableStateFlow(syncPrefs.syncEnabled)
    val syncEnabled: StateFlow<Boolean> = _syncEnabled.asStateFlow()

    // ИСПРАВЛЕНО: refreshYandexStatus() вызывается при старте и после навигации сюда
    private val _yandexAuthorized = MutableStateFlow(yandexRepository.isAuthorized())
    val yandexAuthorized: StateFlow<Boolean> = _yandexAuthorized.asStateFlow()

    private val _availableCalendars = MutableStateFlow<List<DeviceCalendar>>(emptyList())
    val availableCalendars: StateFlow<List<DeviceCalendar>> = _availableCalendars.asStateFlow()

    private val _filterTags = MutableStateFlow(syncPrefs.filterTags.joinToString(","))
    val filterTags: StateFlow<String> = _filterTags.asStateFlow()

    private val _lastSyncTime = MutableStateFlow(syncPrefs.lastSyncTime)
    val lastSyncTimeFormatted: StateFlow<String> = _lastSyncTime.map { ms ->
        if (ms == 0L) "Никогда"
        else SimpleDateFormat("dd.MM.yyyy HH:mm", Locale("ru")).format(Date(ms))
    }.stateIn(viewModelScope, SharingStarted.Lazily, "—")

    private val _conflicts = MutableStateFlow<List<SyncConflictData>>(emptyList())
    val conflicts: StateFlow<List<SyncConflictData>> = _conflicts.asStateFlow()

    private val _syncStatus = MutableStateFlow("")
    val syncStatus: StateFlow<String> = _syncStatus.asStateFlow()

    val hasCalendarPermission: Boolean
        get() = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) ==
                PackageManager.PERMISSION_GRANTED

    // Вызывается из onResume() фрагмента — обновляет статус авторизации
    // (нужно т.к. авторизация происходит в MainActivity, а не во фрагменте)
    fun refreshYandexStatus() {
        _yandexAuthorized.value = yandexRepository.isAuthorized()
    }

    fun setSyncEnabled(enabled: Boolean) {
        syncPrefs.syncEnabled = enabled
        _syncEnabled.value = enabled
        if (enabled) scheduleBackgroundSync() else cancelBackgroundSync()
    }

    fun loadCalendars() {
        if (!hasCalendarPermission) return
        _availableCalendars.value = calendarSyncManager.getAvailableCalendars()
    }

    fun selectCalendar(id: Long) {
        syncPrefs.selectedCalendarId = id
    }

    fun setFilterTags(tags: String) {
        syncPrefs.filterTags = tags.split(",").map { it.trim() }
        _filterTags.value = tags
    }

    fun syncNow() {
        if (!hasCalendarPermission) {
            _syncStatus.value = "Нет разрешения на доступ к календарю"
            return
        }
        viewModelScope.launch {
            _syncStatus.value = "Синхронизация..."
            try {
                // Синхронизация с локальным Calendar Provider
                val conflicts = eventRepository.syncWithDeviceCalendar()
                _conflicts.value = conflicts

                // Если авторизованы в Яндексе — дополнительно синхронизируем с облаком
                if (yandexRepository.isAuthorized()) {
                    val yandexResult = yandexRepository.getYandexEvents()
                    yandexResult.onSuccess { events ->
                        android.util.Log.d("SettingsVM", "Got ${events.size} events from Yandex")
                        // TODO: смерджить events с локальной БД через eventRepository
                    }.onFailure { e ->
                        android.util.Log.e("SettingsVM", "Yandex sync failed", e)
                        _syncStatus.value = "Ошибка Яндекс: ${e.message}"
                        return@launch
                    }
                }

                _lastSyncTime.value = syncPrefs.lastSyncTime
                _syncStatus.value = if (conflicts.isEmpty()) "Синхронизировано ✓"
                else "Найдено ${conflicts.size} конфликт(ов)"
            } catch (e: Exception) {
                _syncStatus.value = "Ошибка: ${e.message}"
            }
        }
    }

    fun resolveConflict(conflictData: SyncConflictData) {
        _conflicts.value = _conflicts.value.filter { it.eventId != conflictData.eventId }
    }

    fun clearYandexAuth() {
        yandexRepository.clearAuth()
        _yandexAuthorized.value = false
    }

    private fun scheduleBackgroundSync() {
        val request = PeriodicWorkRequestBuilder<CalendarSyncWorker>(4, TimeUnit.HOURS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                    .build()
            )
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "calendar_sync", ExistingPeriodicWorkPolicy.UPDATE, request
        )
    }

    private fun cancelBackgroundSync() {
        WorkManager.getInstance(context).cancelUniqueWork("calendar_sync")
    }
}