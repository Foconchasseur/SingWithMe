package com.example.singwithme.ui

import MenuScreen
import KaraokeViewModel
import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.singwithme.back.cache.Song
import com.example.singwithme.objects.CurrentMusicData
import com.example.singwithme.objects.MusicUtils
import com.example.singwithme.objects.Playlist
import com.example.singwithme.repository.MusicRepository
import com.example.singwithme.ui.screens.PlaybackScreenContent
import com.example.singwithme.viewmodel.MusicViewModel
import com.google.android.exoplayer2.ExoPlayer
import java.io.File

@Composable
fun KaraokeNavigation() {
    val currentContext = LocalContext.current
    val playlist = Playlist.songs
    val navController = rememberNavController()
    val karaokeViewModel = KaraokeViewModel()
    val menuViewModel = MusicViewModel(currentContext)

    NavHost(
        navController = navController,
        startDestination = "menu" // Écran de démarrage
    ) {
        composable("menu") {
            Log.d("Menu","Affichage du menu")
            MenuScreen(navController, playlist, menuViewModel::downloadAndSerializeSong, karaokeViewModel::setPlaying)
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



