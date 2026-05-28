package com.example.roombooking.util

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.roombooking.data.repository.EventRepository
import com.example.roombooking.data.repository.SyncPreferences
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class CalendarSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val eventRepository: EventRepository,
    private val syncPrefs: SyncPreferences
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            if (!syncPrefs.syncEnabled || syncPrefs.selectedCalendarId == null) {
                return Result.success()
            }
            val conflicts = eventRepository.syncWithDeviceCalendar()
            // Конфликты сохраняем — пользователь увидит их при открытии настроек
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
