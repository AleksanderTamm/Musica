package com.example.musica

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists")
data class PlaylistEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String?,
    var songs: String?,
)
