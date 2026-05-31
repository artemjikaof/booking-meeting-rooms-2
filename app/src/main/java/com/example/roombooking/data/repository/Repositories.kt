package com.example.roombooking.data.repository

import com.example.roombooking.data.local.*
import com.example.roombooking.domain.model.Event
import com.example.roombooking.domain.model.Room
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

// ─── Mappers ────────────────────────────────────────────────

fun RoomEntity.toDomain() = Room(
    id = id, name = name, capacity = capacity, description = description,
    photoUris = if (photoUris.isBlank()) emptyList() else photoUris.split(",")
)

fun Room.toEntity() = RoomEntity(
    id = id, name = name, capacity = capacity, description = description,
    photoUris = photoUris.joinToString(",")
)

fun EventEntity.toDomain() = Event(
    id = id, title = title, dateStart = dateStart, dateEnd = dateEnd,
    timeStart = timeStart, timeEnd = timeEnd, roomId = roomId, roomName = roomName,
    description = description, participants = participants,
    syncToDeviceCalendar = syncToDeviceCalendar,
    deviceCalendarEventId = deviceCalendarEventId,
    fromDeviceCalendar = fromDeviceCalendar,
    lastModifiedInApp = lastModifiedInApp,
    lastModifiedInCalendar = lastModifiedInCalendar
)

fun Event.toEntity() = EventEntity(
    id = id, title = title, dateStart = dateStart, dateEnd = dateEnd,
    timeStart = timeStart, timeEnd = timeEnd, roomId = roomId, roomName = roomName,
    description = description, participants = participants,
    syncToDeviceCalendar = syncToDeviceCalendar,
    deviceCalendarEventId = deviceCalendarEventId,
    fromDeviceCalendar = fromDeviceCalendar,
    lastModifiedInApp = lastModifiedInApp,
    lastModifiedInCalendar = lastModifiedInCalendar
)

// ─── RoomRepository ──────────────────────────────────────────

@Singleton
class RoomRepository @Inject constructor(private val roomDao: RoomDao) {

    fun getAllRooms(): Flow<List<Room>> =
        roomDao.getAllRooms().map { list -> list.map { it.toDomain() } }

    fun searchRooms(query: String): Flow<List<Room>> =
        roomDao.searchRooms(query).map { list -> list.map { it.toDomain() } }

    suspend fun getRoomById(id: Long): Room? = roomDao.getRoomById(id)?.toDomain()

    suspend fun insertRoom(room: Room): Long = roomDao.insertRoom(room.toEntity())

    suspend fun updateRoom(room: Room) = roomDao.updateRoom(room.toEntity())

    suspend fun deleteRoom(room: Room) = roomDao.deleteRoom(room.toEntity())

    suspend fun hasLinkedEvents(roomId: Long): Boolean =
        roomDao.countEventsByRoom(roomId) > 0
}

// ─── EventRepository ─────────────────────────────────────────

@Singleton
class EventRepository @Inject constructor(
    private val eventDao: EventDao,
    private val calendarSyncManager: CalendarSyncManager,
    private val prefs: SyncPreferences
) {
    fun getAllEvents(): Flow<List<Event>> =
        eventDao.getAllEvents().map { list -> list.map { it.toDomain() } }

    fun getEventsByDate(date: String): Flow<List<Event>> =
        eventDao.getEventsByDate(date).map { list -> list.map { it.toDomain() } }

    fun getEventsByRoom(roomId: Long): Flow<List<Event>> =
        eventDao.getEventsByRoom(roomId).map { list -> list.map { it.toDomain() } }

    fun searchEvents(query: String): Flow<List<Event>> =
        eventDao.searchEvents(query).map { list -> list.map { it.toDomain() } }

    suspend fun getEventById(id: Long): Event? = eventDao.getEventById(id)?.toDomain()

    suspend fun getDatesWithEvents(from: String, to: String): List<String> =
        eventDao.getDatesWithEvents(from, to)

    suspend fun hasConflict(
        roomId: Long, date: String, timeStart: String, timeEnd: String, excludeId: Long = 0L
    ): Boolean = eventDao.findConflicts(roomId, date, timeStart, timeEnd, excludeId).isNotEmpty()

    suspend fun insertEvent(event: Event): Long {
        // Сначала сохраняем в БД — это всегда должно работать
        val id = eventDao.insertEvent(event.toEntity())
        // Синхронизация с системным календарём — опциональна, ошибки игнорируем
        if (event.syncToDeviceCalendar && prefs.syncEnabled) {
            try {
                val calId = prefs.selectedCalendarId
                if (calId != null) {
                    val calEventId = calendarSyncManager.insertEventToCalendar(event, calId)
                    if (calEventId != null) {
                        eventDao.updateEvent(event.copy(id = id, deviceCalendarEventId = calEventId).toEntity())
                    }
                }
            } catch (e: Exception) {
                android.util.Log.w("EventRepository", "Calendar sync failed on insert: ${e.message}")
            }
        }
        return id
    }

    suspend fun updateEvent(event: Event) {
        // Сначала обновляем в БД
        eventDao.updateEvent(event.toEntity())
        // Синхронизация с системным календарём — опциональна, ошибки игнорируем
        if (event.syncToDeviceCalendar && event.deviceCalendarEventId != null && prefs.syncEnabled) {
            try {
                calendarSyncManager.updateEventInCalendar(event.deviceCalendarEventId, event)
            } catch (e: Exception) {
                android.util.Log.w("EventRepository", "Calendar sync failed on update: ${e.message}")
            }
        }
    }

    suspend fun deleteEvent(event: Event) {
        // Сначала удаляем из БД
        eventDao.deleteEvent(event.toEntity())
        // Удаление из системного календаря — опционально, ошибки игнорируем
        if (event.deviceCalendarEventId != null && prefs.syncEnabled) {
            try {
                calendarSyncManager.deleteEventFromCalendar(event.deviceCalendarEventId)
            } catch (e: Exception) {
                android.util.Log.w("EventRepository", "Calendar sync failed on delete: ${e.message}")
            }
        }
    }

    suspend fun syncWithDeviceCalendar(): List<SyncConflictData> {
        if (!prefs.syncEnabled) return emptyList()
        val calendarId = prefs.selectedCalendarId ?: return emptyList()
        val filterTags = prefs.filterTags
        val calEvents = calendarSyncManager.readCalendarEvents(calendarId, filterTags)
        val conflicts = mutableListOf<SyncConflictData>()
        val zone = ZoneId.systemDefault()

        for (calEvent in calEvents) {
            val existing = eventDao.findByCalendarEventId(calEvent.id)
            val startLocal = Instant.ofEpochMilli(calEvent.dtStart).atZone(zone).toLocalDateTime()
            val endLocal = Instant.ofEpochMilli(calEvent.dtEnd).atZone(zone).toLocalDateTime()
            val dateStr = startLocal.toLocalDate().toString()
            val timeStartStr = startLocal.toLocalTime().toString().substring(0, 5)
            val timeEndStr = endLocal.toLocalTime().toString().substring(0, 5)

            if (existing == null) {
                eventDao.insertEvent(EventEntity(
                    title = calEvent.title,
                    dateStart = dateStr,
                    dateEnd = endLocal.toLocalDate().toString(),
                    timeStart = timeStartStr,
                    timeEnd = timeEndStr,
                    roomId = 0,
                    description = calEvent.description,
                    syncToDeviceCalendar = true,
                    deviceCalendarEventId = calEvent.id,
                    fromDeviceCalendar = true,
                    lastModifiedInCalendar = calEvent.dtStart
                ))
            } else {
                val calNowModified = calEvent.dtStart
                val calModified = existing.lastModifiedInCalendar ?: 0L
                if (existing.lastModifiedInApp > calModified && calNowModified != calModified) {
                    conflicts.add(SyncConflictData(existing.id, existing, calEvent))
                } else if (calNowModified != calModified) {
                    eventDao.updateEvent(existing.copy(
                        title = calEvent.title,
                        dateStart = dateStr,
                        timeStart = timeStartStr,
                        timeEnd = timeEndStr,
                        lastModifiedInCalendar = calNowModified
                    ))
                }
            }
        }
        prefs.lastSyncTime = System.currentTimeMillis()
        return conflicts
    }
}

data class SyncConflictData(
    val eventId: Long,
    val appVersion: EventEntity,
    val calendarVersion: CalendarEventData
)