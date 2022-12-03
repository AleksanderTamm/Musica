package com.example.musica.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.musica.MainActivity
import com.example.musica.R
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.android.appremote.api.error.CouldNotFindSpotifyApp
import com.spotify.android.appremote.api.error.NotLoggedInException
import com.spotify.android.appremote.api.error.UserNotAuthorizedException
import com.spotify.protocol.types.Track


/**
 * A simple [Fragment] subclass.
 * Use the [SpotifyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SpotifyFragment : Fragment() {


    //spotify
    private val clientId = "756545fbd8c44cb98826b116cda757ad"
    private val redirectUri = "com.example.musica://callback"
    private var spotifyAppRemote: SpotifyAppRemote? = null

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
        val play= view.findViewById<Button>(R.id.play)
        val pause= view.findViewById<Button>(R.id.pause)


        //voimalik et unsecure
        play.setOnClickListener{
            Toast.makeText(view.context, "play", Toast.LENGTH_SHORT).show()
            playSpotifySong("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL")
        }
        pause.setOnClickListener{
            Toast.makeText(view.context, "pause", Toast.LENGTH_SHORT).show()
            pauseSpotify()
        }
    }

    override fun onPause() {
        Log.i("Spotify", "paused")
        super.onPause()
    }

    override fun onResume() {
        Log.i("Spotify", "resumed")
        super.onResume()
    }



    override fun onStart() {
        super.onStart()

        //spotify
        val connectionParams = ConnectionParams.Builder(clientId)
            .setRedirectUri(redirectUri)
            .showAuthView(true)
            .build()

        SpotifyAppRemote.connect(context, connectionParams, object : Connector.ConnectionListener {
            override fun onConnected(appRemote: SpotifyAppRemote) {
                spotifyAppRemote = appRemote
                Log.d("MainActivity", "Connected! Yay!")
                // Now you can start interacting with App Remote
                connected()
            }
            //ilmselt parem aga tuleb t2ita
            override fun onFailure(error: Throwable?) {
                if (error is NotLoggedInException || error is UserNotAuthorizedException) {
                    // Show login button and trigger the login flow from auth library when clicked
                } else if (error is CouldNotFindSpotifyApp) {
                    // Show button to download Spotify
                }
            }
            //kui see
            /*override fun onFailure(throwable: Throwable) {
                Log.e("MainActivity", throwable.message, throwable)
                // Something went wrong when attempting to connect! Handle errors here
            }*/
        })
    }

    // Set the connection parameters

    private fun connected() {
        // Then we will write some more code here.
        //example playlist
        //spotifyAppRemote?.playerApi?.play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL")
        // Subscribe to PlayerState
        // Subscribe to PlayerState

        spotifyAppRemote?.getPlayerApi()
            ?.subscribeToPlayerState()
            ?.setEventCallback { playerState ->
                val track: Track = playerState.track
                if (track != null) {
                    Log.d("MainActivity", track.name + " by " + track.artist.name)
                }
            }

    }

    fun playSpotifySong(uri: String){
        //"spotify:playlist:37i9dQZF1DX2sUQwD7tbmL"
        spotifyAppRemote?.playerApi?.play(uri)
    }

    fun pauseSpotify(){
        spotifyAppRemote?.playerApi?.pause()
    }


    override fun onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(spotifyAppRemote);
    }



}