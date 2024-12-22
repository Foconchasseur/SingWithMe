package com.example.singwithme

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.singwithme.objects.Playlist
import com.example.singwithme.repository.PlaylistRepository
import com.example.singwithme.ui.KaraokeNavigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KaraokeActivity : ComponentActivity() {

    private lateinit var playlistRepository: PlaylistRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        playlistRepository = PlaylistRepository(applicationContext)

        CoroutineScope(Dispatchers.Main).launch {

            //Playlist.songs.clear()
            if (Playlist.songs.isEmpty()){
                Playlist.songs.addAll(playlistRepository.getMusicData())
            }
            Playlist.cacheFile = playlistRepository.getCacheFile()

            setContent {
                KaraokeNavigation()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
