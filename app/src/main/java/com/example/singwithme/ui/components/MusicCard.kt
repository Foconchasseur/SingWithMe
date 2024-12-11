package com.example.singwithme.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.singwithme.back.cache.Song
import com.example.singwithme.ui.theme.SingWithMeTheme

@Composable
fun MusicCard(
    music: Song
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier//.width(255.dp)
    ) {
        Text(
            text = music.artist,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Text(
            text = music.name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        if (!music.locked) {
            ActionButton(
                icon = Icons.Default.PlayArrow,
                contentDescription = "Menu",
                onClick = {}
            )
        }
    }
}

