package com.example.singwithme.ui.screens

import android.content.Context
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
import com.example.singwithme.data.models.LyricsLine
import com.example.singwithme.ui.components.ActionButton
import com.example.singwithme.ui.components.KaraokeSimpleText
import com.google.android.exoplayer2.ExoPlayer
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun loadLyricsFromAssets(context: Context, fileName: String): String {
    return context.assets.open(fileName).bufferedReader().use { it.readText() }
}


@Composable
fun PlaybackScreen(
    lyrics : List<LyricsLine>,
    exoPlayer: ExoPlayer,
    onPauseClick: () -> Unit,
    onRestartClick: () -> Unit,
    onMenuClick: () -> Unit,
) {

    var currentLyricCount by remember { mutableStateOf(0) }
    var currentLyric by remember { mutableStateOf<LyricsLine?>(null) }
    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        // Prépare le lecteur ExoPlayer et démarre la lecture
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }

    // Synchronisation de l'audio et des paroles
    LaunchedEffect(exoPlayer) {
        while (true) {
            val currentPosition = exoPlayer.currentPosition / 1000f // En secondes
            //Log.d("Timer", "Current position: $currentPosition")
            if (currentLyric != null && currentPosition >= currentLyric!!.endTime) {
                currentLyricCount = lyrics.indexOf(currentLyric) + 1
            }
            val lyric = lyrics.find { it.startTime <= currentPosition && it.endTime > currentPosition }

            //Log.d("Lyric", "Current lyric: $lyric")

            currentLyric = lyric

            // Si on a une ligne valide, calculer le progrès
            currentLyric?.let {
                progress = (currentPosition - it.startTime) / (it.endTime - it.startTime)
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
                onClick = onMenuClick
            )
            ActionButton(
                icon = Icons.Default.PlayArrow,
                contentDescription = "Pause",
                onClick = onPauseClick
            )
            ActionButton(
                icon = Icons.Default.Refresh,
                contentDescription = "Restart",
                onClick = {
                    progress = 0f
                    currentLyricCount = 0
                    onRestartClick()
                }
            )
        }
    }
}

