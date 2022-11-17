package com.example.musica


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.musica.databinding.ActivityMainBinding
import com.example.musica.ui.main.SectionsPagerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.types.Track

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //spotify
    private val clientId = "756545fbd8c44cb98826b116cda757ad"
    private val redirectUri =
        "com.example.musica://callback" //vast pole vaja "https://kodu.ut.ee/~tammalek/spotifyAuth.txt" //atm minu lehel, saab muuta
    private var spotifyAppRemote: SpotifyAppRemote? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = binding.fab

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onStart() {
        super.onStart()

        //spotify
        val connectionParams = ConnectionParams.Builder(clientId)
            .setRedirectUri(redirectUri)
            .showAuthView(true)
            .build()

        SpotifyAppRemote.connect(this, connectionParams, object : Connector.ConnectionListener {
            override fun onConnected(appRemote: SpotifyAppRemote) {
                spotifyAppRemote = appRemote
                Log.d("MainActivity", "Connected! Yay!")
                // Now you can start interacting with App Remote
                connected()
            }

            override fun onFailure(throwable: Throwable) {
                Log.e("MainActivity", throwable.message, throwable)
                // Something went wrong when attempting to connect! Handle errors here
            }
        })
    }

    // Set the connection parameters

    private fun connected() {
        // Then we will write some more code here.
        //example playlist
        spotifyAppRemote?.playerApi?.play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL")
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


    override fun onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(spotifyAppRemote);
    }


}