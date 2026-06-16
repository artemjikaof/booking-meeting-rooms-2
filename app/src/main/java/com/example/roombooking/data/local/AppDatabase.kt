package com.example.roombooking.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RoomEntity::class, EventEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun roomDao(): RoomDao
    abstract fun eventDao(): EventDao
}
