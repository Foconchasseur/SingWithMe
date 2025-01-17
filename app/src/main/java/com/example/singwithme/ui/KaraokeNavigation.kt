package com.example.singwithme.ui

import MenuScreen
import KaraokeViewModel
import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.singwithme.objects.CurrentMusicData
import com.example.singwithme.objects.MusicUtils
import com.example.singwithme.repository.PlaylistRepository
import com.example.singwithme.ui.components.ErrorDisplay
import com.example.singwithme.ui.screens.PlaybackScreenContent
import com.example.singwithme.viewmodel.DownloadViewModel
import com.example.singwithme.viewmodel.ErrorViewModel
import com.example.singwithme.viewmodel.FilterViewModel
import com.example.singwithme.viewmodel.ThemeViewModel
import kotlinx.coroutines.launch
import java.io.File
import androidx.compose.material3.Typography
import androidx.compose.runtime.collectAsState
import com.example.singwithme.objects.Playlist
import com.example.singwithme.ui.theme.CustomColorScheme
import com.example.singwithme.ui.theme.DarkColorScheme
import com.example.singwithme.ui.theme.JapanColorScheme
import com.example.singwithme.ui.theme.LightColorScheme


@Composable
fun KaraokeNavigation(
    playlistRepository: PlaylistRepository,
    karaokeViewModel: KaraokeViewModel,

) {

    val errorViewModel = ErrorViewModel()
    val currentContext = LocalContext.current
    val themeViewModel = ThemeViewModel(currentContext)
    val navController = rememberNavController()
    val downloadViewModel = DownloadViewModel(currentContext)
    val quitApplication: () -> Unit = {
        quitApplication(karaokeViewModel, context = currentContext)
    }
    val coroutineScope = rememberCoroutineScope() // Scope de coroutine associé au composable
    val filterViewModel = FilterViewModel()

    val currentThemeIndex by themeViewModel.currentThemeIndex.collectAsState()
    val themes = listOf(LightColorScheme, DarkColorScheme, CustomColorScheme, JapanColorScheme)


    MaterialTheme(
        colorScheme = themes[currentThemeIndex],
        typography = Typography(),
        shapes = Shapes()
    ) {
        // L'UI de ton application ici
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            NavHost(
                navController = navController,
                startDestination = "menu" // Écran de démarrage
            ) {

                composable("menu") {
                    ErrorDisplay(
                        errorViewModel
                    )
                    Log.d("Menu","Affichage du menu")
                    Log.d("Playlist",Playlist.songs.toList().toString())
                    MenuScreen(
                        navController = navController,
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
                        filterViewModel = filterViewModel,
                        themeViewModel = themeViewModel,
                        currentThemeIndex = currentThemeIndex
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

                    PlaybackScreenContent(navController, karaokeViewModel)
                }
            }
        }
    }

}

fun quitApplication(karaokeViewModel: KaraokeViewModel, context: Context){
    karaokeViewModel.release()
    (context as? Activity)?.finishAffinity()
}





