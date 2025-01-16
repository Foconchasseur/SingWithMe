package com.example.singwithme.ui.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.singwithme.data.models.ID
import com.example.singwithme.data.models.Song
import com.example.singwithme.viewmodel.ErrorViewModel

@Composable
fun SongCard(
    song: Song,
    downloadFunction: (ID, ErrorViewModel, MutableState<List<Song>>) -> Unit,
    setPlayingTrue: (Boolean) -> Unit,
    deleteFiles: (Context, String, ID, MutableState<List<Song>>) -> Unit,
    navController: NavController,
    errorViewModel: ErrorViewModel,
    songList : MutableState<List<Song>>
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
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(0.2f)
        ){
            Text(
                text = song.id.artist,
                style = MaterialTheme.typography.titleLarge,
            )
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(0.5f)
        ){
            Text(
                text = song.id.name,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(0.3f) // 70% de la largeur*
        ){

            if (!song.locked) {
                if (!song.downloaded) {
                    Box(
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ){
                        Button(
                            onClick = {
                                Log.d("Button Download", "Le bouton a ete appuyé sur la musique "+ song.id.name)
                                song.id?.let { downloadFunction(it, errorViewModel, songList) }
                            },
                            modifier = Modifier
                        ) {
                            Text("Télécharger")
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Download Icon"
                            )
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        ActionButton(
                            icon = Icons.Default.Delete, // Bouton lecture
                            contentDescription = "Delete",
                            onClick = {
                                val fileName = song.path.substringBefore(".").replace("/","_")
                                song.id?.let { deleteFiles(currentContext,fileName, it, songList) }
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

    }
}

