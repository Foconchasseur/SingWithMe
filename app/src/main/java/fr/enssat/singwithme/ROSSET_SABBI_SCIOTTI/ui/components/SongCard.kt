package fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.ui.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
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
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.ID
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.viewmodel.ErrorViewModel

/**
 * SongCard est un composant permettant d'afficher une carte de musique avec des actions associées
 * @param song : Song, la musique à afficher
 * @param downloadFilesSong : (ID, ErrorViewModel, MutableState<List<Song>>) -> Unit, la fonction de téléchargement des paroles et du ficher audio
 * @param setPlayingTrue : (Boolean) -> Unit, la fonction qui met à jour l'état de lecture lorsqu'une musique est lancée
 * @param deleteFiles : (Context, String, ID, MutableState<List<Song>>) -> Unit, la fonction de suppression des fichiers audio et des paroles
 * @param navController : NavController, le contrôleur de navigation afin de lancer l'écran de lecture de la musique
 * @param errorViewModel : ErrorViewModel, le viewModel qui gère les erreurs de l'application (nécessaire pour downloadFilesSong)
 * @param songList : MutableState<List<Song>>, la liste des chansons filtrées (nécessaire pour downloadFilesSong et deleteFiles)
 */
@Composable
fun SongCard(
    song: fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song,
    downloadFilesSong: (fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.ID, ErrorViewModel, MutableState<List<fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song>>) -> Unit,
    setPlayingTrue: (Boolean) -> Unit,
    deleteFiles: (Context, String, fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.ID, MutableState<List<fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song>>) -> Unit,
    navController: NavController,
    errorViewModel: ErrorViewModel,
    songList : MutableState<List<fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song>>
) {
    val currentContext = LocalContext.current
    val backgroundColor = if (song.locked) {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f) // Grisé si locked
    } else {
        MaterialTheme.colorScheme.surface
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
                .weight(0.3f)
        ){

            if (!song.locked) {
                if (!song.downloaded) {
                    Box(
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ){
                        Button(
                            onClick = {
                                Log.i("SongCard", "Téléchargement des fichiers de la musique "+ song.id.name)
                                song.id?.let { downloadFilesSong(it, errorViewModel, songList) }
                            },
                            modifier = Modifier
                        ) {
                            Text("Télécharger")
                            Spacer(modifier = Modifier.size(4.dp))
                            Icon(
                                imageVector = Icons.Filled.Download,
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
                            icon = Icons.Default.Delete,
                            contentDescription = "Delete",
                            onClick = {
                                Log.i("SongCard", "Suppression des fichiers de la musique "+ song.id.name)
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

