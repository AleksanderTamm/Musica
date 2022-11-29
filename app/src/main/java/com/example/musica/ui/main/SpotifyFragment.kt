package com.example.musica.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.musica.MainActivity
import com.example.musica.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SpotifyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SpotifyFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




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
            (activity as MainActivity?)?.playSpotifySong("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL")
        }
        pause.setOnClickListener{
            Toast.makeText(view.context, "pause", Toast.LENGTH_SHORT).show()
            (activity as MainActivity?)?.pauseSpotify()
        }
    }




}