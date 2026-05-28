package com.example.roombooking.data.repository

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.provider.CalendarContract
import com.example.roombooking.domain.model.Event
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalendarSyncManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val cr: ContentResolver get() = context.contentResolver

    private fun toMillis(dateStr: String, timeStr: String): Long {
        val date = LocalDate.parse(dateStr)
        val time = LocalTime.parse(timeStr)
        return date.atTime(time).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    fun insertEventToCalendar(event: Event, calendarId: Long): Long? {
        val values = ContentValues().apply {
            put(CalendarContract.Events.CALENDAR_ID, calendarId)
            put(CalendarContract.Events.TITLE, event.title)
            put(CalendarContract.Events.DTSTART, toMillis(event.dateStart, event.timeStart))
            put(CalendarContract.Events.DTEND, toMillis(event.dateEnd, event.timeEnd))
            put(CalendarContract.Events.EVENT_LOCATION, event.roomName)
            put(CalendarContract.Events.DESCRIPTION, event.description)
            put(CalendarContract.Events.EVENT_TIMEZONE, ZoneId.systemDefault().id)
        }
        val uri = cr.insert(CalendarContract.Events.CONTENT_URI, values)
        return uri?.lastPathSegment?.toLongOrNull()
    }

    fun updateEventInCalendar(calendarEventId: Long, event: Event) {
        val values = ContentValues().apply {
            put(CalendarContract.Events.TITLE, event.title)
            put(CalendarContract.Events.DTSTART, toMillis(event.dateStart, event.timeStart))
            put(CalendarContract.Events.DTEND, toMillis(event.dateEnd, event.timeEnd))
            put(CalendarContract.Events.EVENT_LOCATION, event.roomName)
            put(CalendarContract.Events.DESCRIPTION, event.description)
        }
        val uri = CalendarContract.Events.CONTENT_URI.buildUpon()
            .appendPath(calendarEventId.toString()).build()
        cr.update(uri, values, null, null)
    }

    fun deleteEventFromCalendar(calendarEventId: Long) {
        val uri = CalendarContract.Events.CONTENT_URI.buildUpon()
            .appendPath(calendarEventId.toString()).build()
        cr.delete(uri, null, null)
    }

    fun readCalendarEvents(calendarId: Long, filterTags: List<String>): List<CalendarEventData> {
        val result = mutableListOf<CalendarEventData>()
        val projection = arrayOf(
            CalendarContract.Events._ID,
            CalendarContract.Events.TITLE,
            CalendarContract.Events.DESCRIPTION,
            CalendarContract.Events.EVENT_LOCATION,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND
        )
        val cursor = cr.query(
            CalendarContract.Events.CONTENT_URI, projection,
            "${CalendarContract.Events.CALENDAR_ID} = ?",
            arrayOf(calendarId.toString()), null
        ) ?: return result

        cursor.use {
            while (it.moveToNext()) {
                val id = it.getLong(0)
                val title = it.getString(1) ?: ""
                val description = it.getString(2) ?: ""
                val location = it.getString(3) ?: ""
                val dtStart = it.getLong(4)
                val dtEnd = it.getLong(5)
                val matchesTag = filterTags.isEmpty() ||
                        filterTags.any { tag -> title.contains(tag) || description.contains(tag) }
                if (matchesTag) result.add(CalendarEventData(id, title, description, location, dtStart, dtEnd))
            }
        }
        return result
    }

    fun getAvailableCalendars(): List<DeviceCalendar> {
        val result = mutableListOf<DeviceCalendar>()
        val projection = arrayOf(
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
            CalendarContract.Calendars.ACCOUNT_NAME
        )
        val cursor = cr.query(CalendarContract.Calendars.CONTENT_URI, projection, null, null, null)
            ?: return result
        cursor.use {
            while (it.moveToNext()) {
                result.add(DeviceCalendar(it.getLong(0), it.getString(1) ?: "", it.getString(2) ?: ""))
            }
        }
        return result
    }
}

data class CalendarEventData(
    val id: Long, val title: String, val description: String,
    val location: String, val dtStart: Long, val dtEnd: Long
)

data class DeviceCalendar(val id: Long, val displayName: String, val accountName: String)
