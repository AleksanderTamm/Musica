package com.example.musica

import android.annotation.SuppressLint
import android.app.Application
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import com.CodeBoy.MediaFacer.AudioGet
import com.CodeBoy.MediaFacer.MediaFacer

class SongViewModel(val app: Application): AndroidViewModel(app) {

    val songArray = mutableListOf(R.raw.song_sample, R.raw.beat_sth, R.raw.lifelike, R.raw.motivational, R.raw.mountain_path3, R.raw.password, R.raw.please_calm)

    @SuppressLint("Recycle", "Range")
    fun getAudioFiles(): ArrayList<String>{


        // --------------- first possible solution?? -------------------------
        /*
        try {
            Log.i("songysum", Environment.getExternalStorageDirectory().toString())
            val path = Environment.getExternalStorageDirectory().toString()+"/Download"
            val directory: File = File(path)
            Log.d("songySongy", directory.name)
            directory.walkTopDown().forEach {
                Log.d("songy12345", it.name)
            }
            val files = directory.listFiles()
            files.forEach {
                Log.d("Files", it.name)
            }
            Log.d("Files", "Size: " + files.size)
        }catch (e: Exception){
            Log.i("songyE", e.toString())
        }

         */




        // ----------------------- second solution ?? --------------------------
        /*
        var selection: String = MediaStore.Audio.Media.IS_MUSIC + " != 0"
        var projection = arrayOf(MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.ARTIST)
        val cursor = requireContext().contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, MediaStore.Audio.Media.DATE_ADDED + " DESC", null)
        if (cursor != null){
            if (cursor.moveToFirst()){
                do {
                    val title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))?:"Unknown"
                    val id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))?:"Unknown"
                    val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))?:"Unknown"
                    val path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    val uri = Uri.parse("content://media/external/audio/albumart")
                    /*
                    val music = Music(id = idC, title = titleC, album = albumC, artist = artistC, path = pathC, duration = durationC,
                        artUri = artUriC)
                    val file = File(music.path)
                    if(file.exists())
                        audioContents.add(music)

                     */
                    Log.i("songy123", "$id $title $path $uri")
                }while (cursor.moveToNext())
                cursor.close()
            }
        }
         */


        //  ------------- third possible solution?? -------------------------
        /*
        val audioContents = MediaFacer
            .withAudioContex(requireContext())
            .getAllAudioContent(AudioGet.externalContentUri)
        val proj = arrayOf(MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME)
        audioContents.forEach {
            Log.i("songy1", it.toString())
        }
         */

        val x = arrayListOf(" hakajhs")

        return x
    }
}