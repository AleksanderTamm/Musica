package com.example.musica

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.SeekBar
import androidx.navigation.findNavController
import com.example.musica.databinding.ActivityMediaPlayerBinding
import java.lang.Exception
import androidx.navigation.fragment.findNavController


class MediaPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediaPlayerBinding
    private var mp: MediaPlayer? = null
    private var currentSong = mutableListOf(R.raw.song_sample)
    private var index: Int = 0
    private var songs: MutableList<Int> = mutableListOf()
    private var newSong: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intentSongs = intent.getStringExtra("playlist")
        Log.i("songys", intentSongs + " ")
        val songsList = intentSongs?.split(' ')
        songsList?.forEach {
            Log.i("songys1", it + " ")
        }

        songsList?.forEach {
            if (it != "" )
                songs.add(it.toInt())
        }
        controlSound(songs.get(0))


    }

    private fun controlSound(id: Int) {
        binding.playButton.setOnClickListener{
            if (mp == null){
                mp = MediaPlayer.create(this, id)
                Log.d("MediaPlayer", "ID: ${mp!!.audioSessionId}")

                initialiseSeekBar()
            }
            else if(newSong){
                mp = MediaPlayer.create(this, id)
                Log.d("MediaPlayer", "ID: ${mp!!.audioSessionId}")
                initialiseSeekBar()
                newSong = false
            }
            mp?.start()
            Log.d("MediaPlayer", "Duration: ${mp!!.duration/1000} seconds")
        }

        binding.stopButton.setOnClickListener {
            if(mp !== null) mp?.pause()
            Log.d("MediaPlayer", "Paused at: ${mp!!.currentPosition/1000} seconds")
        }

        binding.replayButton.setOnClickListener {
            if(mp !== null){
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
                index = 0
            }
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mp?.seekTo(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

    }

    private fun initialiseSeekBar(){
        binding.seekBar.max = mp!!.duration

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable{
            override fun run() {
                try {
                    binding.seekBar.progress = mp!!.currentPosition
                    val x = mp!!.currentPosition
                    val y = mp!!.duration
                    if (mp!!.currentPosition == mp!!.duration){
                        if(index < songs.size - 1){
                            val song: Int = songs.get(index+1)
                            mp = MediaPlayer.create(this@MediaPlayerActivity, song)
                            binding.seekBar.max = mp!!.duration
                            mp?.start()
                            index++
                        }
                    }
                    handler.postDelayed(this, 1000)
                } catch (e: Exception){
                    binding.seekBar.progress = 0
                }
            }
        }, 0)
    }
}