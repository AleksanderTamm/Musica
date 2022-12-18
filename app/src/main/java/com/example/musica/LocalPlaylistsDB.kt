package com.example.musica

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [ PlaylistEntity::class ], version = 1)
abstract class LocalPlaylistsDB : RoomDatabase() {

    abstract fun getPlaylistDao(): PlaylistsDao

    companion object {
        private lateinit var PlaylistDb : LocalPlaylistsDB

        @Synchronized fun getInstance(context: Context) : LocalPlaylistsDB {

            if (!this::PlaylistDb.isInitialized) {
                PlaylistDb = Room.databaseBuilder(
                    context, LocalPlaylistsDB::class.java, "playlistDb")
                    .fallbackToDestructiveMigration() // each time schema changes, data is lost!
                    .allowMainThreadQueries() // if possible, use background thread instead
                    .build()
            }
            return PlaylistDb

        }
    }

}