package com.example.singwithme.ui

import MenuScreen
import KaraokeViewModel
import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.singwithme.objects.CurrentMusicData
import com.example.singwithme.objects.MusicUtils
import com.example.singwithme.objects.Playlist
import com.example.singwithme.repository.PlaylistRepository
import com.example.singwithme.ui.components.LaunchScreen
import com.example.singwithme.ui.screens.PlaybackScreenContent
import com.example.singwithme.viewmodel.DownloadViewModel
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun KaraokeNavigation(
    playlistRepository: PlaylistRepository
) {
    val currentContext = LocalContext.current
    val playlist = Playlist.songs
    val navController = rememberNavController()
    val karaokeViewModel = KaraokeViewModel()
    val downloadViewModel = DownloadViewModel(currentContext)
    val quitApplication: () -> Unit = {
        quitApplication(karaokeViewModel, context = currentContext)
    }
    val coroutineScope = rememberCoroutineScope() // Scope de coroutine associé au composable

    NavHost(
        navController = navController,
        startDestination = "menu" // Écran de démarrage
    ) {
        composable("menu") {
            Log.d("Menu","Affichage du menu")

                MenuScreen(
                    navController = navController,
                    playlist = playlist,
                    downloadFunction = downloadViewModel::downloadAndSerializeSong,
                    setPlayingTrue = karaokeViewModel::setPlaying,
                    deleteFiles = downloadViewModel::deleteDownloadedFiles,
                    quitApplication = quitApplication,
                    downloadPlaylist = {
                        coroutineScope.launch {
                            playlistRepository.downloadPlaylist()
                        }
                    }
                )

        }
        composable("playback/{fileName}") { backStackEntry ->
            val fileName = backStackEntry.arguments?.getString("fileName") ?: ""
            Log.d("Filename",fileName)
            val music = MusicUtils.loadMusicFromCache(currentContext, "$fileName.ser")
            CurrentMusicData.lyrics = music.lyrics
            val mp3FilePath = File(currentContext.cacheDir, "$fileName.mp3").absolutePath
            karaokeViewModel.initializePlayer(currentContext,mp3FilePath.toUri())

            PlaybackScreenContent(navController, karaokeViewModel, music.lyrics)
        }
    }
}

fun quitApplication(karaokeViewModel: KaraokeViewModel, context: Context){
    karaokeViewModel.release()
    (context as? Activity)?.finishAffinity()
}





