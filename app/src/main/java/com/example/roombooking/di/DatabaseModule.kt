package com.example.roombooking.di

import android.content.Context
import androidx.room.Room
import com.example.roombooking.data.local.AppDatabase
import com.example.roombooking.data.local.EventDao
import com.example.roombooking.data.local.RoomDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "room_booking.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideRoomDao(db: AppDatabase): RoomDao = db.roomDao()

    @Provides
    fun provideEventDao(db: AppDatabase): EventDao = db.eventDao()
}
