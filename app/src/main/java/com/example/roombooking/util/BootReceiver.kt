package com.example.roombooking.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.*
import com.example.roombooking.data.repository.SyncPreferences
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject lateinit var syncPrefs: SyncPreferences

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED && syncPrefs.backgroundSyncEnabled) {
            val interval = syncPrefs.backgroundSyncIntervalHours.toLong()
            val request = PeriodicWorkRequestBuilder<CalendarSyncWorker>(interval, TimeUnit.HOURS)
                .setConstraints(Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                    .build())
                .build()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "calendar_sync",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
    }
}
