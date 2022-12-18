package com.example.musica

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musica.databinding.FragmentMainBinding
import com.example.musica.databinding.FragmentPlaylistsBinding
import com.example.musica.ui.main.PageViewModel


class Playlists : Fragment() {

    private var _binding: FragmentPlaylistsBinding? = null
    private lateinit var playlistAdapter: PlaylistAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val model: PlaylistViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPlaylistsBinding.inflate(inflater, container, false)
        val root = binding.root
        setUpRecyclerView()

        //binding.button.setOnClickListener { openMediaPlayer() }
        binding.floatingActionButton.setOnClickListener {
            openAddPlaylist()
        }
        return root
    }

    override fun onResume(){
        super.onResume()
        model.refresh()
        playlistAdapter.data = model.playlistArray
        playlistAdapter.notifyDataSetChanged()
    }

    fun startMediaPlayer(playlist: PlaylistEntity){
        val intent = Intent(requireContext(), MediaPlayerActivity::class.java )
        intent.putExtra("playlist", playlist.songs)
        startActivity(intent)
    }

    private fun setUpRecyclerView() {
        val playlistClickListener = PlaylistAdapter.PlayClickListener { p -> startMediaPlayer(p) }
        playlistAdapter = PlaylistAdapter(model.playlistArray, playlistClickListener)
        binding.ListOfPlay.adapter = playlistAdapter
        binding.ListOfPlay.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPlaylistsBinding.bind(view)
    }

    private fun openMediaPlayer() {
        startActivity(Intent(requireContext(), MediaPlayerActivity::class.java))
    }

    fun openAddPlaylist(){
        val bundle = Bundle()
        findNavController().navigate(R.id.action_AddPlaylist, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}