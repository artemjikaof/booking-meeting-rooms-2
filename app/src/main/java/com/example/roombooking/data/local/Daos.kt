package com.example.roombooking.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {

    @Query("SELECT * FROM rooms ORDER BY name ASC")
    fun getAllRooms(): Flow<List<RoomEntity>>

    @Query("SELECT * FROM rooms WHERE id = :id")
    suspend fun getRoomById(id: Long): RoomEntity?

    @Query("SELECT * FROM rooms WHERE name LIKE '%' || :query || '%'")
    fun searchRooms(query: String): Flow<List<RoomEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoom(room: RoomEntity): Long

    @Update
    suspend fun updateRoom(room: RoomEntity)

    @Delete
    suspend fun deleteRoom(room: RoomEntity)

    @Query("SELECT COUNT(*) FROM events WHERE roomId = :roomId")
    suspend fun countEventsByRoom(roomId: Long): Int
}

@Dao
interface EventDao {

    @Query("SELECT * FROM events ORDER BY dateStart ASC, timeStart ASC")
    fun getAllEvents(): Flow<List<EventEntity>>

    @Query("SELECT * FROM events WHERE dateStart = :date ORDER BY timeStart ASC")
    fun getEventsByDate(date: String): Flow<List<EventEntity>>

    @Query("SELECT * FROM events WHERE roomId = :roomId ORDER BY dateStart ASC, timeStart ASC")
    fun getEventsByRoom(roomId: Long): Flow<List<EventEntity>>

    @Query("SELECT * FROM events WHERE title LIKE '%' || :query || '%' ORDER BY dateStart ASC")
    fun searchEvents(query: String): Flow<List<EventEntity>>

    @Query("""
        SELECT * FROM events
        WHERE roomId = :roomId
          AND dateStart = :date
          AND id != :excludeId
          AND NOT (timeEnd <= :timeStart OR timeStart >= :timeEnd)
    """)
    suspend fun findConflicts(
        roomId: Long,
        date: String,
        timeStart: String,
        timeEnd: String,
        excludeId: Long = 0L
    ): List<EventEntity>

    @Query("SELECT * FROM events WHERE syncToDeviceCalendar = 1")
    suspend fun getSyncableEvents(): List<EventEntity>

    @Query("SELECT * FROM events WHERE deviceCalendarEventId = :calEventId LIMIT 1")
    suspend fun findByCalendarEventId(calEventId: Long): EventEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity): Long

    @Update
    suspend fun updateEvent(event: EventEntity)

    @Delete
    suspend fun deleteEvent(event: EventEntity)

    @Query("DELETE FROM events WHERE id = :id")
    suspend fun deleteEventById(id: Long)

    @Query("SELECT DISTINCT dateStart FROM events WHERE dateStart BETWEEN :from AND :to")
    suspend fun getDatesWithEvents(from: String, to: String): List<String>

    @Query("SELECT * FROM events WHERE id = :id LIMIT 1")
    suspend fun getEventById(id: Long): EventEntity?
}
