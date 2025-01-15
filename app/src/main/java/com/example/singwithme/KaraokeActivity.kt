package com.example.singwithme

import KaraokeViewModel
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModelProvider
import com.example.singwithme.data.models.Song
import com.example.singwithme.objects.Playlist
import com.example.singwithme.repository.PlaylistRepository
import com.example.singwithme.ui.KaraokeNavigation
import com.google.android.exoplayer2.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KaraokeActivity : ComponentActivity() {

    private lateinit var playlistRepository: PlaylistRepository

    private val karaokeViewModel: KaraokeViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        playlistRepository = PlaylistRepository(applicationContext)

        CoroutineScope(Dispatchers.Main).launch {

            playlistRepository.iniliazePlaylist()
            Playlist.cacheFile = playlistRepository.getCacheFile()

            setContent {
                KaraokeNavigation(playlistRepository, karaokeViewModel)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("KaraokeActivity", "onPause")
        karaokeViewModel.pause()
    }

    override fun onResume() {
        super.onResume()
        Log.d("KaraokeActivity", "onResume")
        karaokeViewModel.pause()
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}
