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
    yandexEventId = yandexEventId,
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
    yandexEventId = yandexEventId,
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
    private val roomDao: RoomDao,
    private val calendarSyncManager: CalendarSyncManager,
    private val prefs: SyncPreferences,
    private val yandexRepository: YandexCalendarRepository
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

    // Ищем комнату по названию из location события Яндекса/Calendar
    private suspend fun resolveRoomByName(locationName: String): Pair<Long, String> {
        if (locationName.isBlank()) return Pair(0L, "")
        val allRooms: List<RoomEntity> = roomDao.getAllRoomsSync()
        val match: RoomEntity? = allRooms.firstOrNull { roomEntity: RoomEntity ->
            roomEntity.name.equals(locationName.trim(), ignoreCase = true) ||
                    locationName.trim().contains(roomEntity.name, ignoreCase = true) ||
                    roomEntity.name.contains(locationName.trim(), ignoreCase = true)
        }
        return if (match != null) {
            android.util.Log.d("EventRepo", "Room matched: '${match.name}' for '$locationName'")
            Pair(match.id, match.name)
        } else {
            android.util.Log.d("EventRepo", "No room match for '$locationName'")
            Pair(0L, locationName)
        }
    }

    suspend fun insertEvent(event: Event): Long {
        val id = eventDao.insertEvent(event.toEntity())

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
                android.util.Log.w("EventRepo", "Calendar sync failed on insert: ${e.message}")
            }
        }

        if (yandexRepository.isAuthorized()) {
            try {
                val yandexId = yandexRepository.syncBookingToYandex(
                    summary = event.title,
                    description = event.description,
                    start = "${event.dateStart}T${event.timeStart}:00+03:00",
                    end = "${event.dateEnd}T${event.timeEnd}:00+03:00",
                    location = event.roomName ?: "",
                    externalId = id.toString()
                ).getOrNull()
                if (yandexId != null) {
                    eventDao.updateEvent(eventDao.getEventById(id)!!.copy(yandexEventId = yandexId))
                }
            } catch (e: Exception) {
                android.util.Log.e("EventRepo", "Yandex sync failed during insert", e)
            }
        }

        return id
    }

    suspend fun updateEvent(event: Event) {
        eventDao.updateEvent(event.toEntity())

        if (event.syncToDeviceCalendar && event.deviceCalendarEventId != null && prefs.syncEnabled) {
            try {
                calendarSyncManager.updateEventInCalendar(event.deviceCalendarEventId, event)
            } catch (e: Exception) {
                android.util.Log.w("EventRepo", "Calendar sync failed on update: ${e.message}")
            }
        }

        if (yandexRepository.isAuthorized() && event.yandexEventId != null) {
            try {
                yandexRepository.updateYandexEvent(
                    yandexEventId = event.yandexEventId,
                    summary = event.title,
                    description = event.description,
                    start = "${event.dateStart}T${event.timeStart}:00+03:00",
                    end = "${event.dateEnd}T${event.timeEnd}:00+03:00",
                    location = event.roomName ?: "",
                    externalId = event.id.toString()
                )
            } catch (e: Exception) {
                android.util.Log.e("EventRepo", "Yandex update failed: ${e.message}")
            }
        }
    }

    suspend fun deleteEvent(event: Event) {
        eventDao.deleteEvent(event.toEntity())

        if (event.deviceCalendarEventId != null && prefs.syncEnabled) {
            try {
                calendarSyncManager.deleteEventFromCalendar(event.deviceCalendarEventId)
            } catch (e: Exception) {
                android.util.Log.w("EventRepo", "Calendar sync failed on delete: ${e.message}")
            }
        }

        if (yandexRepository.isAuthorized() && event.yandexEventId != null) {
            try {
                yandexRepository.deleteYandexEvent(event.yandexEventId)
            } catch (e: Exception) {
                android.util.Log.e("EventRepo", "Yandex delete failed: ${e.message}")
            }
        }
    }

    // ИСПРАВЛЕНО: Яндекс синхронизируется независимо от syncEnabled/selectedCalendarId
    // syncEnabled влияет только на системный Calendar Provider
    suspend fun syncWithDeviceCalendar(): List<SyncConflictData> {
        val conflicts = mutableListOf<SyncConflictData>()
        val zone = ZoneId.systemDefault()

        android.util.Log.d("EventRepo", "=== SYNC START: syncEnabled=${prefs.syncEnabled}, calId=${prefs.selectedCalendarId}, yandex=${yandexRepository.isAuthorized()} ===")

        // ── Системный Calendar Provider ──────────────────────────────────────
        if (prefs.syncEnabled) {
            val calendarId: Long? = prefs.selectedCalendarId
            if (calendarId == null) {
                android.util.Log.w("EventRepo", "syncEnabled=true но календарь не выбран!")
            } else {
                val filterTags: List<String> = prefs.filterTags
                android.util.Log.d("EventRepo", "Device calendar $calendarId, tags=$filterTags")
                val calEvents: List<CalendarEventData> = calendarSyncManager.readCalendarEvents(calendarId, filterTags)
                android.util.Log.d("EventRepo", "Device calendar events: ${calEvents.size}")

                for (calEvent in calEvents) {
                    val existing: EventEntity? = eventDao.findByCalendarEventId(calEvent.id)
                    val startLocal = Instant.ofEpochMilli(calEvent.dtStart).atZone(zone).toLocalDateTime()
                    val endLocal = Instant.ofEpochMilli(calEvent.dtEnd).atZone(zone).toLocalDateTime()
                    val dateStr = startLocal.toLocalDate().toString()
                    val timeStartStr = startLocal.toLocalTime().toString().substring(0, 5)
                    val timeEndStr = endLocal.toLocalTime().toString().substring(0, 5)

                    if (existing == null) {
                        val (roomId, roomName) = resolveRoomByName(calEvent.location)
                        eventDao.insertEvent(EventEntity(
                            title = calEvent.title,
                            dateStart = dateStr,
                            dateEnd = endLocal.toLocalDate().toString(),
                            timeStart = timeStartStr,
                            timeEnd = timeEndStr,
                            roomId = roomId,
                            roomName = roomName,
                            description = calEvent.description,
                            syncToDeviceCalendar = true,
                            deviceCalendarEventId = calEvent.id,
                            fromDeviceCalendar = true,
                            lastModifiedInCalendar = calEvent.dtStart
                        ))
                        android.util.Log.d("EventRepo", "Inserted from device calendar: '${calEvent.title}'")
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
            }
        }

        // ── Яндекс Календарь — независимо от syncEnabled ────────────────────
        if (yandexRepository.isAuthorized()) {
            try {
                android.util.Log.d("EventRepo", "Fetching Yandex events...")
                val yandexResult: Result<List<CalendarEventData>> = yandexRepository.getYandexEvents()

                yandexResult.onFailure { e: Throwable ->
                    android.util.Log.e("EventRepo", "Yandex fetch failed: ${e.message}")
                }

                yandexResult.onSuccess { yEvents: List<CalendarEventData> ->
                    android.util.Log.d("EventRepo", "Yandex events: ${yEvents.size}")
                    val allLocal: List<EventEntity> = eventDao.getAllEventsSync()

                    // Импортируем из Яндекса в приложение
                    for (yEvent in yEvents) {
                        val yIdStr: String = yEvent.id.toString()
                        val existing: EventEntity? = allLocal.find { it.yandexEventId == yIdStr }

                        if (existing == null) {
                            val startLocal2 = Instant.ofEpochMilli(yEvent.dtStart).atZone(zone).toLocalDateTime()
                            val endLocal2 = Instant.ofEpochMilli(yEvent.dtEnd).atZone(zone).toLocalDateTime()
                            val (roomId, roomName) = resolveRoomByName(yEvent.location)

                            eventDao.insertEvent(EventEntity(
                                title = yEvent.title,
                                dateStart = startLocal2.toLocalDate().toString(),
                                dateEnd = endLocal2.toLocalDate().toString(),
                                timeStart = startLocal2.toLocalTime().toString().substring(0, 5),
                                timeEnd = endLocal2.toLocalTime().toString().substring(0, 5),
                                roomId = roomId,
                                roomName = roomName,
                                description = yEvent.description,
                                yandexEventId = yIdStr,
                                fromDeviceCalendar = false
                            ))
                            android.util.Log.d("EventRepo", "Inserted from Yandex: '${yEvent.title}', room='$roomName'")
                        }
                    }

                    // Пушим в Яндекс события из приложения без yandexEventId
                    val toPush: List<EventEntity> = allLocal.filter { e: EventEntity ->
                        e.yandexEventId == null && !e.fromDeviceCalendar && e.title.isNotBlank()
                    }
                    android.util.Log.d("EventRepo", "Events to push to Yandex: ${toPush.size}")

                    for (event: EventEntity in toPush) {
                        try {
                            val yId: String? = yandexRepository.syncBookingToYandex(
                                summary = event.title,
                                description = event.description,
                                start = "${event.dateStart}T${event.timeStart}:00+03:00",
                                end = "${event.dateEnd}T${event.timeEnd}:00+03:00",
                                location = event.roomName ?: "",
                                externalId = event.id.toString()
                            ).getOrNull()

                            if (yId != null) {
                                eventDao.updateEvent(event.copy(yandexEventId = yId))
                                android.util.Log.d("EventRepo", "Pushed to Yandex: '${event.title}'")
                            }
                        } catch (e: Exception) {
                            android.util.Log.e("EventRepo", "Push failed '${event.title}': ${e.message}")
                        }
                    }
                }
            } catch (e: Exception) {
                android.util.Log.e("EventRepo", "Yandex sync block failed", e)
            }
        } else {
            android.util.Log.d("EventRepo", "Yandex not authorized, skipping")
        }

        prefs.lastSyncTime = System.currentTimeMillis()
        android.util.Log.d("EventRepo", "=== SYNC END, conflicts=${conflicts.size} ===")
        return conflicts
    }
}

data class SyncConflictData(
    val eventId: Long,
    val appVersion: EventEntity,
    val calendarVersion: CalendarEventData
)