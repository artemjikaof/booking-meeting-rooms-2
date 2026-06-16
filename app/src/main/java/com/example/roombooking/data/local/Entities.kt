package com.example.roombooking.data.local

import androidx.room.*
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "rooms")
data class RoomEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val capacity: Int,
    val description: String = "",
    val photoUris: String = ""
)

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val dateStart: String,   // ISO string "YYYY-MM-DD"
    val dateEnd: String,
    val timeStart: String,   // "HH:mm"
    val timeEnd: String,
    val roomId: Long,
    val roomName: String = "",
    val description: String = "",
    val participants: String = "",
    val syncToDeviceCalendar: Boolean = false,
    val deviceCalendarEventId: Long? = null,
    val yandexEventId: String? = null,
    val fromDeviceCalendar: Boolean = false,
    val lastModifiedInApp: Long = System.currentTimeMillis(),
    val lastModifiedInCalendar: Long? = null
)

// TypeConverters больше не нужны — используем String для дат
