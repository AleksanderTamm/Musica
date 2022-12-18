package com.example.musica

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlaylistsDao {

    @Query("SELECT * FROM playlists")
    fun loadPlaylists(): Array<PlaylistEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlaylist(vararg playlist: PlaylistEntity)

}