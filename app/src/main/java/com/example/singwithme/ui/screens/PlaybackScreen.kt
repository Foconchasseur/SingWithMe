package com.example.singwithme.ui.screens

import KaraokeViewModel
import android.content.Context
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
import com.example.singwithme.data.models.LyricsLine
import com.example.singwithme.objects.CurrentMusicData
import com.example.singwithme.ui.components.ActionButton
import com.example.singwithme.ui.components.KaraokeSimpleText
import com.example.singwithme.ui.components.KaraokeSlider
import com.example.singwithme.ui.pxToDp
import kotlinx.coroutines.delay

fun loadLyricsFromAssets(context: Context, fileName: String): String {
    return context.assets.open(fileName).bufferedReader().use { it.readText() }
}

@Composable
fun PlaybackScreenContent(navController: NavHostController, karaokeViewModel: KaraokeViewModel) {
    PlaybackScreen(
        karaokeViewModel = karaokeViewModel,
        onMenuClick = {navController.navigate("menu")},
    )
}

@Composable
fun PlaybackScreen(
    karaokeViewModel: KaraokeViewModel,
    onMenuClick: () -> Unit,
) {
    val lyrics = CurrentMusicData.lyrics
    var currentLyricCount by remember { mutableStateOf(0) }
    var currentLyric by remember { mutableStateOf<LyricsLine?>(null) }
    var progress by remember { mutableStateOf(0f) }
    var isRunning by remember { mutableStateOf(true) } // Contrôle du LaunchedEffect
    var paused by remember { mutableStateOf(false) }
    val duration = lyrics.last().startTime
    // Synchronisation de l'audio et des paroles
    LaunchedEffect(isRunning) {
        Log.d("LaucnhedEffect", "boucle")
        while (isRunning) {
            val currentPosition = karaokeViewModel.getCurrentPosition()?.div(1000f) // En seconde
            currentLyric =
                lyrics.find { it.startTime <= currentPosition!! && it.endTime > currentPosition }
            currentLyric?.let {
                if (currentPosition != null) {
                    progress = (currentPosition - it.startTime) / (it.endTime - it.startTime)
                }
            }
            currentLyricCount = lyrics.indexOf(currentLyric)
            delay(50L) // Vérifier toutes les 100ms
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
                lastText = lyrics.getOrNull(currentLyricCount + 1)?.text ?: "",
                nextText = lyrics.getOrNull(currentLyricCount - 1)?.text ?: "",
                progress = progress,
                modifier = Modifier
                    .align(Alignment.CenterStart) // Centre le texte verticalement
                    .fillMaxWidth() // Le texte occupe toute la largeur,

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
                        currentLyricCount = 0
                        karaokeViewModel.reset()
                    }
                )
            }
        }

    }

}


