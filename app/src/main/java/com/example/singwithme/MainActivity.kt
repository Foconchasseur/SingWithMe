package com.example.singwithme

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.singwithme.back.cache.WorkerCachePlaylist
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkInfo.State
import com.example.singwithme.data.models.Music
import com.example.singwithme.back.Lyrics.WorkerMd2Lyrics
import com.example.singwithme.back.cache.Playlist
import com.example.singwithme.back.cache.Song
import com.example.singwithme.back.cache.WorkerCacheSong
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileReader


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        val savePlaylistRequest = OneTimeWorkRequestBuilder<WorkerCachePlaylist>().build()
        val saveMusicRequest = OneTimeWorkRequestBuilder<WorkerCacheSong>().build()
        val md2LyricsRequest = OneTimeWorkRequestBuilder<WorkerMd2Lyrics>().build()

        WorkManager.getInstance(this)
            .beginWith(savePlaylistRequest)
            .then(saveMusicRequest)
            .then(md2LyricsRequest)
            .enqueue()

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(savePlaylistRequest.id).observe(this) { workInfo ->
            if (workInfo != null && workInfo.state == State.SUCCEEDED) {
                val playlist = Playlist()
                playlist.songs = readJsonFromCache(this)
                println(playlist)
            }
        }

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(md2LyricsRequest.id).observe(this) { workInfo ->
            if (workInfo != null && workInfo.state == State.SUCCEEDED) {
                val music = Music.deserializeObjectFromCache(this, "Bohemian Rapsodie.ser")
                Log.e("Music", music.toString())

                val intent = Intent(this, KaraokeActivity::class.java).apply {
                    putExtra("MUSIC_EXTRA", music) // Pour Serializable
                    // ou
                    // putExtra("MUSIC_EXTRA", music) // Pour Parcelable
                }
                startActivity(intent)

                // Terminer MainActivity
                finish()
            }
        }
    }

    fun readJsonFromCache(context: Context): List<Song>? {
        val cacheDir = context.cacheDir
        val jsonFile = File(cacheDir, "playlist.json")
        if (jsonFile.exists()) {
            FileReader(jsonFile).use { reader ->
                val gson = Gson()
                val songListType = object : TypeToken<List<Song>>() {}.type
                return gson.fromJson(reader, songListType)
            }
        }
        return null
    }


}








