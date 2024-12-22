package com.example.singwithme.ui.screens

import KaraokeViewModel
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.singwithme.data.models.LyricsLine
import com.example.singwithme.objects.CurrentMusicData
import com.example.singwithme.ui.components.ActionButton
import com.example.singwithme.ui.components.KaraokeSimpleText
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun loadLyricsFromAssets(context: Context, fileName: String): String {
    return context.assets.open(fileName).bufferedReader().use { it.readText() }
}

@Composable
fun PlaybackScreenContent(navController: NavHostController, karaokeViewModel: KaraokeViewModel, lyrics : List<LyricsLine>) {
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
    Log.d("lyrics",lyrics.toString())
    var currentLyricCount by remember { mutableStateOf(0) }
    var currentLyric by remember { mutableStateOf<LyricsLine?>(null) }
    var progress by remember { mutableStateOf(0f) }
    var isRunning by remember { mutableStateOf(true) } // Contrôle du LaunchedEffect


    // Synchronisation de l'audio et des paroles
    LaunchedEffect(isRunning) {
        Log.d("LaucnhedEffect","boucle")
        while (isRunning) {
            val currentPosition = karaokeViewModel.getCurrentPosition()?.div(1000f) // En secondes
            if (currentPosition != null) {
                if (currentLyric != null && currentPosition >= currentLyric!!.endTime) {
                    currentLyricCount = lyrics.indexOf(currentLyric) + 1
                }
            }
            val lyric = lyrics.find { it.startTime <= currentPosition!! && it.endTime > currentPosition }

            //Log.d("Lyric", "Current lyric: $lyric")

            currentLyric = lyric

            // Si on a une ligne valide, calculer le progrès
            currentLyric?.let {
                if (currentPosition != null) {
                    progress = (currentPosition - it.startTime) / (it.endTime - it.startTime)
                }
            }
            //Log.d("Progression", "Current progression: $progress")
            // Si on dépasse la fin de la ligne, on passe à la suivante

            //Log.d("LyricCount", "Current lyric count: $currentLyricCount")
            delay(50L   ) // Vérifier toutes les 100ms
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        KaraokeSimpleText(
            mainText = lyrics.getOrNull(currentLyricCount)?.text ?: "",
            lastText = lyrics.getOrNull(currentLyricCount + 1)?.text ?: "",
            nextText = lyrics.getOrNull(currentLyricCount - 1)?.text ?: "",
            progress = progress,
            modifier = Modifier
                .align(Alignment.CenterStart) // Centre le texte à la fois verticalement
                .fillMaxWidth() // Le texte occupe toute la largeur,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(10.dp)
                .padding(top = 5.dp)
            ,
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
            ActionButton(
                icon = Icons.Default.PlayArrow,
                contentDescription = "Pause",
                onClick = {
                    karaokeViewModel.pause()
                }
            )
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


