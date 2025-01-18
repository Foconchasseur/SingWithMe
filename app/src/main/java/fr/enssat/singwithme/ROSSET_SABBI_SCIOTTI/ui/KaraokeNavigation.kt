package fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.ui

import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.ui.screens.MenuScreen
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.viewmodel.KaraokeViewModel
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
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.objects.CurrentMusicData
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.objects.MusicUtils
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.repository.PlaylistRepository
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.ui.components.ErrorDisplay
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.ui.screens.PlaybackScreenContent
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.viewmodel.DownloadViewModel
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.viewmodel.ErrorViewModel
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.viewmodel.FilterViewModel
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.viewmodel.ThemeViewModel
import kotlinx.coroutines.launch
import java.io.File
import androidx.compose.material3.Typography
import androidx.compose.runtime.collectAsState
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.objects.Playlist
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.ui.theme.BlueColorScheme
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.ui.theme.DarkColorScheme
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.ui.theme.JapanColorScheme
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.ui.theme.LightColorScheme

/**
 * Element parent de tous les composables
 * @param playlistRepository : com.example.ROSSET_SABBY_SCIOTTI.repository.PlaylistRepository, gère la logique de téléchargement de la liste de musique
 * @param karaokeViewModel : com.example.ROSSET_SABBY_SCIOTTI.viewmodel.KaraokeViewModel, le viewModel qui gère ExoPlayer pour la lecture de la chanson
 */
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
    val themes = listOf(LightColorScheme, DarkColorScheme, BlueColorScheme, JapanColorScheme)


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
                    Log.d("Playlist", Playlist.songs.toList().toString())
                    MenuScreen(
                        navController = navController,
                        downloadFilesSong = downloadViewModel::downloadAndSerializeSong,
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
                    val music = fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.objects.MusicUtils.loadMusicFromCache(currentContext, "$fileName.ser")
                    fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.objects.CurrentMusicData.lyrics = music.lyrics
                    val mp3FilePath = File(currentContext.cacheDir, "$fileName.mp3").absolutePath
                    PlaybackScreenContent(navController, karaokeViewModel, mp3FilePath.toUri(),currentContext)
                }
            }
        }
    }

}

/**
 * fonction pour quitter l'application en cliquant sur un bouton
 * @param karaokeViewModel : com.example.ROSSET_SABBY_SCIOTTI.viewmodel.KaraokeViewModel, le viewModel qui gère ExoPlayer pour la lecture de la chanson
 * @param context : Context, le contexte actuel de l'application
 */
fun quitApplication(karaokeViewModel: KaraokeViewModel, context: Context){
    karaokeViewModel.release()
    (context as? Activity)?.finishAffinity()
}





