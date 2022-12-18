package com.example.musica

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class PlaylistViewModel(val app: Application): AndroidViewModel(app) {
    var playlistArray : Array<PlaylistEntity> = arrayOf()

    fun refresh(){
        val db = LocalPlaylistsDB.getInstance(app)
        val lists = db.getPlaylistDao().loadPlaylists()
        playlistArray = lists
    }

}