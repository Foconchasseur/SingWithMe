package com.example.singwithme.ui.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.singwithme.data.models.Song

@Composable
fun SongCard(
    song: Song,
    onCardClick: (Song) -> Unit = {},
    downloadFunction: (String) -> Unit,
    setPlayingTrue: (Boolean) -> Unit,
    deleteFiles: (Context, String, String) -> Unit,
    navController: NavController
) {
    val currentContext = LocalContext.current
    val backgroundColor = if (song.locked) {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f) // Grisé si locked
    } else {
        MaterialTheme.colorScheme.surface // Couleur normale
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .background(color = backgroundColor)
            .let {
                if (!song.locked && song.downloaded) {
                    it.clickable { onCardClick(song) } // Rendre cliquable si downloaded
                } else {
                    it
                }
            }
            .padding(16.dp)
    ) {
        Text(
            text = song.artist,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = song.name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(2f)
        )
        if (!song.locked) {
            if (!song.downloaded) {
                Button(
                    onClick = {
                        Log.d("Button Download", "Le bouton a ete appuyé sur la musique "+ song.name)
                        song.id?.let { downloadFunction(it) }
                    },
                    modifier = Modifier
                ) {
                    Text(text = if (song.locked) "Téléchargement en cours..." else "Télécharger")
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Download Icon"
                    )
                }
            } else {
                ActionButton(
                    icon = Icons.Default.Delete, // Bouton lecture
                    contentDescription = "Delete",
                    onClick = {
                        val fileName = song.path.substringBefore(".").replace("/","_")
                        song.id?.let { deleteFiles(currentContext,fileName, it) }
                    }
                )
                ActionButton(
                    icon = Icons.Default.PlayArrow, // Bouton lecture
                    contentDescription = "Play",
                    onClick = {
                        setPlayingTrue(true)
                        val fileName = song.path.substringBefore(".").replace("/","_")
                        navController.navigate("playback/$fileName")
                    }
                )
            }
        }
    }
}

