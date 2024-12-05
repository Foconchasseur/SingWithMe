package com.example.singwithme.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.singwithme.data.models.parseLyrics
import com.example.singwithme.ui.components.ActionButton
import com.example.singwithme.ui.components.KaraokeSimpleText
import com.example.singwithme.ui.components.KaraokeText

fun loadLyricsFromAssets(context: Context, fileName: String): String {
    return context.assets.open(fileName).bufferedReader().use { it.readText() }
}

@Composable
fun PlaybackScreen(
    lyricsPath: String,
    onPauseClick: () -> Unit,
    onRestartClick: () -> Unit,
    onMenuClick: () -> Unit,
    songs: List<String>,
    onSongSelected: (String) -> Unit
) {

    val context = LocalContext.current
    val lyricsContent = remember { loadLyricsFromAssets(context, "oasis.txt") }
    val parsedLyrics = parseLyrics(lyricsContent)

    Box(modifier = Modifier.fillMaxSize()) {

        KaraokeText(list = parsedLyrics, modifier = Modifier
            .align(Alignment.Center) // Centre le texte à la fois horizontalement et verticalement
            .fillMaxWidth() // Le texte occupe toute la largeur
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ActionButton(
                icon = Icons.Default.Phone,
                contentDescription = "Pause",
                onClick = onPauseClick
            )
            ActionButton(
                icon = Icons.Default.Refresh,
                contentDescription = "Restart",
                onClick = onRestartClick
            )
            ActionButton(
                icon = Icons.Default.Menu,
                contentDescription = "Menu",
                onClick = { /*coroutineScope.launch { drawerState.open() } */ }
            )
        }
    }
}

