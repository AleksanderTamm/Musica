package com.example.musica

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SongAdapter(var data: MutableList<Int> = mutableListOf(), private var listener: SongClickListener ) : RecyclerView.Adapter<SongAdapter.SongViewHolder>()  {

    fun interface SongClickListener {
        fun onSongClick(song: Int)
    }


    inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_song, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongAdapter.SongViewHolder, position: Int) {
        val song = data[position]

        holder.itemView.apply {
            //this.resources.getString(song)
            val file = this.resources.getString(song).split('/')[2].split('.')[0]
            findViewById<TextView>(R.id.SongTitle).text = file
            setOnClickListener {
                listener.onSongClick(song)
            }
        }
    }

    override fun getItemCount(): Int  = data.size


}