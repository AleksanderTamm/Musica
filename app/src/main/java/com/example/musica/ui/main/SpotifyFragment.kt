package com.example.musica.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.musica.PlayingState
import com.example.musica.R
import com.example.musica.SpotifyService


class SpotifyFragment : Fragment() {
    lateinit var playButton: ImageButton
    lateinit var pauseButton: ImageButton
    lateinit var resumeButton: ImageButton
    lateinit var nextButton: ImageButton
    lateinit var trackImageView: ImageView
    lateinit var songname: TextView


    //spotify
    /*  private val clientId = "756545fbd8c44cb98826b116cda757ad"
      private val redirectUri = "com.example.musica://callback"
      private var spotifyAppRemote: SpotifyAppRemote? = null
  */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Spotify", "created")


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_spotify, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SpotifyService.connect(requireView().context) {}

        playButton = view.findViewById<ImageButton>(R.id.playButton)
        pauseButton = view.findViewById<ImageButton>(R.id.pauseButton)
        resumeButton = view.findViewById<ImageButton>(R.id.resumeButton)
        trackImageView = view.findViewById<ImageView>(R.id.trackImageView)
        nextButton = view.findViewById<ImageButton>(R.id.nextButton)
        songname = view.findViewById<TextView>(R.id.songname)
        setupViews()
        setupListeners()


    }


    override fun onPause() {
        Log.i("Spotify", "paused")
        super.onPause()
    }

    override fun onResume() {
        Log.i("Spotify", "resumed")
        super.onResume()
    }




    override fun onStop() {
        super.onStop()
        SpotifyService.disconnect()
    }

    private fun setupViews() {

        SpotifyService.getCurrentTrackImage {
            trackImageView.setImageBitmap(it)
        }



        SpotifyService.playingState {
            when (it) {
                PlayingState.PLAYING -> showPauseButton()
                PlayingState.STOPPED -> showPlayButton()
                PlayingState.PAUSED -> showResumeButton()
            }
        }
    }

    private fun setupListeners() {
        playButton.setOnClickListener {

            SpotifyService.play("spotify:playlist:37i9dQZF1EIYE32WUF6sxN")

            updateImage()
            showPauseButton()
            updateSongname()
        }


        pauseButton.setOnClickListener {
            SpotifyService.pause()
            updateImage()
            showResumeButton()
            updateSongname()
        }

        resumeButton.setOnClickListener {
            SpotifyService.resume()
            updateImage()
            showPauseButton()
            updateSongname()
        }
        nextButton.setOnClickListener {
            SpotifyService.next()
            showPauseButton()

            updateImage()
            updateSongname()


        }

        SpotifyService.suscribeToChanges {
            Log.i("sth", "suscribeToChanges")
            SpotifyService.getImage(it.imageUri) {
                trackImageView.setImageBitmap(it)
            }


        }
    }

    private fun showPlayButton() {
        playButton.visibility = View.VISIBLE
        pauseButton.visibility = View.GONE
        resumeButton.visibility = View.GONE
    }

    private fun updateImage() {
        SpotifyService.getCurrentTrack {
            SpotifyService.getImage(it.imageUri) {
                trackImageView.setImageBitmap(it)
            }
        }
    }

    private fun updateSongname() {
        SpotifyService.getCurrentTrack {
                songname.text= it.name+" by "+it.artist.name

        }
    }


    private fun showPauseButton() {
        playButton.visibility = View.GONE
        pauseButton.visibility = View.VISIBLE
        resumeButton.visibility = View.GONE
    }

    private fun showResumeButton() {
        playButton.visibility = View.GONE
        pauseButton.visibility = View.GONE
        resumeButton.visibility = View.VISIBLE
    }


}
