package com.example.musica

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlaylistAdapter(
    var data: Array<PlaylistEntity> = arrayOf(),
    private var listener: PlaylistAdapter.PlayClickListener) : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    fun interface PlayClickListener {
        fun onSongClick(recipe: PlaylistEntity)
    }


    inner class PlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_playlist, parent, false)
        return PlaylistViewHolder(view)
    }

    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val playlist = data[position]

        holder.itemView.apply {
            //this.resources.getString(song)
            findViewById<TextView>(R.id.PlayTitle).text = playlist.name
            setOnClickListener {listener.onSongClick(playlist)}
        }
    }

}