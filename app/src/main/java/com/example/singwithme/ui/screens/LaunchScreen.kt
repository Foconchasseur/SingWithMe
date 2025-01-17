package com.example.singwithme.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.singwithme.data.models.Song

/**
 * LaunchScreen est l'écran de lancement de l'application lorsqu'aucune playlist n'est téléchargée
 * @param downloadPlaylist : (MutableState<List<Song>>) -> Unit, la fonction de téléchargement de la playlist
 * @param songList : MutableState<List<Song>>, la liste des chansons téléchargées (nécessaire pour downloadPlaylist)
 */
@Composable
fun LaunchScreen(
    downloadPlaylist : (MutableState<List<Song>>) -> Unit,
    songList : MutableState<List<Song>>
    ) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    )
    {
        Text(
        text = "Bienvenue sur SingWithMe",
        fontSize = 30.sp,
        )
        Spacer(
            modifier = Modifier.size(10.dp)
        )
        Text(
            text = "Veuillez télécharger la",
            style = TextStyle(
                fontSize = 30.sp,
            )
        )
        Text(
            text = "playlist pour commencer",
            style = TextStyle(
                fontSize = 30.sp,
            )
        )
        Spacer(
            modifier = Modifier.size(10.dp)
        )
        Button(
            onClick = { downloadPlaylist(songList) },
            content = {
                Column {
                    Text(
                        text = "Télécharger la playlist",
                        style = TextStyle(
                            fontSize = 20.sp,
                        )
                    )
                    Icon(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        imageVector = Icons.Filled.CloudDownload,
                        contentDescription = "Télécharger la playlist",
                    )
                }
            }
        )
    }
}