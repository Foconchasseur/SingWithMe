package com.example.singwithme

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.singwithme.back.cache.WorkerCachePlaylist
import com.example.singwithme.ui.theme.SingWithMeTheme
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkInfo.State
import com.example.singwithme.back.Lyrics.Music
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
        println("Hello")

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
            }
        }

        setContent {
            SingWithMeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SingWithMeTheme {
        Greeting("Android")
    }
}