package com.example.musica


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.CodeBoy.MediaFacer.AudioGet
import com.CodeBoy.MediaFacer.MediaFacer
import com.example.musica.databinding.FragmentAddPlaylistBinding
import com.example.musica.databinding.SingleSongBinding


class AddPlaylistFragment : Fragment() {
    private lateinit var binding: FragmentAddPlaylistBinding
    private lateinit var songAdapter: SongAdapter
    private var playlist: MutableList<Int> = mutableListOf()
    val model: SongViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!checkPermission()){
            requestPermission()
        }
        if (!checkPermission2()){
            requestPermission2()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_playlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddPlaylistBinding.bind(view)
        setupRecyclerView()
        var testSongList = mutableListOf(R.raw.song_sample, R.raw.beat_sth, R.raw.lifelike, R.raw.motivational, R.raw.mountain_path3, R.raw.password, R.raw.please_calm)

        binding.backButton.setOnClickListener{
            backToMain()
        }
        binding.SavePlaylist.setOnClickListener{
            savePlaylist()
        }
    }

    fun checkPermission(): Boolean{
        val result = ContextCompat.checkSelfPermission( requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
        if (result == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    fun checkPermission2(): Boolean{
        val result = ContextCompat.checkSelfPermission( requireContext(), Manifest.permission.ACCESS_MEDIA_LOCATION)
        if (result == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }


    fun requestPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(requireContext(), "READ PERMISSION IS REQUIRED, PLEASE ALLOW FROM SETTING", Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 123)
        }
    }

    fun requestPermission2(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_MEDIA_LOCATION)){
            Toast.makeText(requireContext(), "ACCESS PERMISSION IS REQUIRED, PLEASE ALLOW FROM SETTING", Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_MEDIA_LOCATION), 123)
        }
    }

    fun savePlaylist(){
        if (playlist.isEmpty())
            Toast.makeText(requireContext(), "No songs selected", Toast.LENGTH_SHORT).show()
        else if(binding.playlistEditText.text.isEmpty()){
            Toast.makeText(requireContext(), "Add playlist name", Toast.LENGTH_SHORT).show()
        }
        else{
            var songs = ""
            playlist.forEach { song ->
                songs += "$song "
            }
            val playlistEntity = PlaylistEntity(0, binding.playlistEditText.text.toString(), songs)
            LocalPlaylistsDB.getInstance(requireContext()).getPlaylistDao().insertPlaylist(playlistEntity)
            backToMain()
        }
    }

    fun backToMain(){
        val bundle = Bundle()
        findNavController().navigate(R.id.action_backToMain, bundle)
    }

    fun addSong(song: Int){
        if (playlist.contains(song))
            playlist.remove(song)
        else {
            playlist.add(song)
        }
    }


    private fun setupRecyclerView() {
        val songClickListener = SongAdapter.SongClickListener { p -> addSong(p)}
        songAdapter = SongAdapter(model.songArray, songClickListener)
        binding.recylerviewAddSongs.adapter = songAdapter
        binding.recylerviewAddSongs.layoutManager = LinearLayoutManager(requireContext())
    }


}