package com.example.musica

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.SeekBar
import com.example.musica.databinding.ActivityMediaPlayerBinding
import java.lang.Exception

class MediaPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediaPlayerBinding
    private var mp: MediaPlayer? = null
    private var currentSong = mutableListOf(R.raw.song_sample)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controlSound(currentSong[0])
    }

    private fun controlSound(id: Int) {
        binding.playButton.setOnClickListener{
            if (mp == null){
                mp = MediaPlayer.create(this, id)
                Log.d("MediaPlayer", "ID: ${mp!!.audioSessionId}")

                initialiseSeekBar()
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
                    handler.postDelayed(this, 1000)
                } catch (e: Exception){
                    binding.seekBar.progress = 0
                }
            }
        }, 0)
    }


}