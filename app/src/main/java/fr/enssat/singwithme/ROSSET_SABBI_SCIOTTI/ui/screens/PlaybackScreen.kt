package fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.ui.screens

import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.viewmodel.KaraokeViewModel
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.LyricsLine
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.objects.CurrentMusicData
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.ui.components.ActionButton
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.ui.components.KaraokeSimpleText
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.ui.components.KaraokeSlider
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.ui.pxToDp
import kotlinx.coroutines.delay

/**
 * Affiche le contenue du karaoke
 * @param navController : NavController, le contrôleur de navigation necessaire pour créer la fonction de retour au menu
 * @param karaokeViewModel : com.example.ROSSET_SABBY_SCIOTTI.viewmodel.KaraokeViewModel, le viewModel qui gère ExoPlayer pour la lecture de la chanson
 */
@Composable
fun PlaybackScreenContent(
    navController: NavHostController,
    karaokeViewModel: KaraokeViewModel,
    uri: Uri,
    context: Context
) {
    PlaybackScreen(
        karaokeViewModel = karaokeViewModel,
        onMenuClick = {navController.navigate("menu")},
        uri = uri,
        context = context
    )
}

/**
 * PlaybackScreen affiche le karaoke avec le défilement de texte et les composants pour gérer la musique
 * @param karaokeViewModel : com.example.ROSSET_SABBY_SCIOTTI.viewmodel.KaraokeViewModel, le viewModel qui gère ExoPlayer pour la lecture de la chanson
 * @param onMenuClick : permet de revenir au menu
 */
@Composable
fun PlaybackScreen(
    karaokeViewModel: KaraokeViewModel,
    onMenuClick: () -> Unit,
    uri: Uri,
    context: Context
) {
    //récupère les lyrics de la musique
    val lyrics = fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.objects.CurrentMusicData.lyrics
    var currentLyricIndex by remember { mutableStateOf(0) }
    var currentLyric by remember { mutableStateOf<fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.LyricsLine?>(null) }
    var progress by remember { mutableStateOf(0f) }
    var paused by remember { mutableStateOf(false) }
    val duration = lyrics.last().startTime
    // Synchronisation de l'audio et des paroles
    LaunchedEffect(Unit) {
        Log.d("PlaybackScreen", "Lancement du karaoke")
        karaokeViewModel.initializePlayer(context = context, uri = uri)
        while (true) {
            val currentPosition = karaokeViewModel.getCurrentPosition()?.div(1000f) // En seconde
            currentLyric =
                lyrics.find { it.startTime <= currentPosition!! && it.endTime > currentPosition }
            currentLyric?.let {
                if (currentPosition != null) {
                    progress = (currentPosition - it.startTime) / (it.endTime - it.startTime)
                }
            }
            currentLyricIndex = lyrics.indexOf(currentLyric)
            delay(50L) // Vérifier toutes les 50ms l'état de la musique
        }
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val screenHeight = constraints.maxHeight
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.70f)
        ) {
            KaraokeSimpleText(
                mainText = currentLyric?.text ?: "",
                lastText = lyrics.getOrNull(currentLyricIndex + 1)?.text ?: "",
                nextText = lyrics.getOrNull(currentLyricIndex - 1)?.text ?: "",
                progress = progress,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .fillMaxWidth()
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.15f)
                .offset(y = (screenHeight * 0.7f).pxToDp())
        ) {
            KaraokeSlider(Modifier.align(Alignment.TopCenter), karaokeViewModel, duration)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.15f)
                .offset(y = (screenHeight * 0.85f).pxToDp())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(10.dp)
                    .padding(top = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActionButton(
                    icon = Icons.Default.Menu,
                    contentDescription = "Menu",
                    onClick = {
                        karaokeViewModel.stop()
                        onMenuClick()
                    }
                )
                if (paused) {
                    ActionButton(
                        icon = Icons.Filled.PlayArrow,
                        contentDescription = "Play",
                        onClick = {
                            karaokeViewModel.pause()
                            paused = false
                        }
                    )
                } else {
                    ActionButton(
                        icon = Icons.Filled.Pause,
                        contentDescription = "Pause",
                        onClick = {
                            karaokeViewModel.pause()
                            paused = true
                        }
                    )
                }
                ActionButton(
                    icon = Icons.Default.Refresh,
                    contentDescription = "Restart",
                    onClick = {
                        progress = 0f
                        currentLyricIndex = 0
                        karaokeViewModel.reset()
                    }
                )
            }
        }
    }
}


