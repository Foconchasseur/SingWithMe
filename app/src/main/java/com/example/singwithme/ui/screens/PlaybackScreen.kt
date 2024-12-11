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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun loadLyricsFromAssets(context: Context, fileName: String): String {
    return context.assets.open(fileName).bufferedReader().use { it.readText() }
}


@Composable
fun PlaybackScreen(
    lyrics : List<LyricsLine>,
    onPauseClick: () -> Unit,
    onRestartClick: () -> Unit,
    onMenuClick: () -> Unit,
) {

    var load = true
    var isRunning by remember { mutableStateOf(true) }
    var timerValue by remember { mutableStateOf(0) }
    var currentLyricCount by remember { mutableStateOf(0) }
    var currentProgress by remember { mutableStateOf(0F) }
    var currentLyric by remember { mutableStateOf(lyrics[currentLyricCount]) }
    val coroutineScope = rememberCoroutineScope()
    var job by remember { mutableStateOf<Job?>(null) }

    fun startTimer() {
        job = coroutineScope.launch {
            while (isRunning) {
                delay(10L) // Délai de 0.01 seconde
                timerValue++
                if (timerValue.toFloat() / 100 > lyrics[currentLyricCount].endTime) {
                    Log.d("Timer","timerValue: $timerValue")
                    currentLyricCount++
                    if (currentLyricCount >= lyrics.size) {
                        break
                    }
                }
                var currentLyric = lyrics[currentLyricCount]
                currentProgress = ((timerValue.toFloat()-1) / 100 - currentLyric.startTime) / (currentLyric.endTime - currentLyric.startTime)
            }
        }
    }
    LaunchedEffect(Unit) {
        startTimer()
    }

    val context = LocalContext.current
    val lyricsContent = remember { loadLyricsFromAssets(context, "oasis.txt") }

    Box(modifier = Modifier.fillMaxSize()) {

        KaraokeSimpleText(
            mainText = lyrics.getOrNull(currentLyricCount)?.text ?: "",
            lastText = lyrics.getOrNull(currentLyricCount + 1)?.text ?: "",
            nextText = lyrics.getOrNull(currentLyricCount - 1)?.text ?: "",
            progress = currentProgress,
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
                onClick = onPauseClick
            )
            ActionButton(
                icon = if (isRunning) Icons.Default.ShoppingCart else Icons.Default.PlayArrow,                contentDescription = "Pause",
                onClick = { isRunning = !isRunning
                    if (isRunning) {
                        startTimer()
                    } else {
                        job?.cancel()
                    }}
            )
            ActionButton(
                icon = Icons.Default.Refresh,
                contentDescription = "Restart",
                onClick = {
                    timerValue = 0
                    currentLyricCount = 0
                    currentProgress = 0F
                    currentLyric = lyrics[currentLyricCount]
                    job?.cancel()
                    startTimer()

                }
            )
        }
    }
}

