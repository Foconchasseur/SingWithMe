package com.example.singwithme.ui

import MenuScreen
import KaraokeViewModel
import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.singwithme.objects.CurrentMusicData
import com.example.singwithme.objects.MusicUtils
import com.example.singwithme.objects.Playlist
import com.example.singwithme.repository.PlaylistRepository
import com.example.singwithme.ui.components.ErrorDisplay
import com.example.singwithme.ui.components.LaunchScreen
import com.example.singwithme.ui.screens.PlaybackScreenContent
import com.example.singwithme.viewmodel.DownloadViewModel
import com.example.singwithme.viewmodel.ErrorViewModel
import com.example.singwithme.viewmodel.FilterViewModel
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun KaraokeNavigation(
    playlistRepository: PlaylistRepository
) {
    val errorViewModel = ErrorViewModel()
    val currentContext = LocalContext.current
    val playlist = Playlist.songs
    val navController = rememberNavController()
    val karaokeViewModel = KaraokeViewModel()
    val downloadViewModel = DownloadViewModel(currentContext)
    val quitApplication: () -> Unit = {
        quitApplication(karaokeViewModel, context = currentContext)
    }
    val coroutineScope = rememberCoroutineScope() // Scope de coroutine associé au composable
    val filterViewModel = FilterViewModel()
    NavHost(
        navController = navController,
        startDestination = "menu" // Écran de démarrage
    ) {

        composable("menu") {
            val filteredItems = Playlist.songs.toList().filter { song ->
                song.id.artist.contains(filterViewModel.getFilter(), ignoreCase = true) || song.id.name.contains(filterViewModel.text, ignoreCase = true)
            }
            ErrorDisplay(
                errorViewModel
            )
            Log.d("Menu","Affichage du menu")
                MenuScreen(
                    navController = navController,
                    playlist = filteredItems,
                    downloadFunction = downloadViewModel::downloadAndSerializeSong,
                    setPlayingTrue = karaokeViewModel::setPlaying,
                    deleteFiles = downloadViewModel::deleteDownloadedFiles,
                    quitApplication = quitApplication,
                    downloadPlaylist =
                    { songs ->
                        coroutineScope.launch {
                            playlistRepository.downloadPlaylist(errorViewModel,songs)
                        }
                    },
                    errorViewModel = errorViewModel,
                    filterViewModel = filterViewModel
                )

        }
        composable("playback/{fileName}") { backStackEntry ->
            ErrorDisplay(
                errorViewModel
            )
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





