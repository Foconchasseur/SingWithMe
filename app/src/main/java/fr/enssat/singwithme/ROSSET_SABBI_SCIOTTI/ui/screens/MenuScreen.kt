package fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.ID
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.objects.Playlist
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.ui.components.ActionButton
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.ui.components.FilterText
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.ui.components.SongGridCard
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.ui.components.ThemeSelector
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.ui.pxToDp
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.viewmodel.ErrorViewModel
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.viewmodel.FilterViewModel
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.viewmodel.ThemeViewModel

/**
 * com.example.ROSSET_SABBY_SCIOTTI.ui.screens.MenuScreen est l'écran principal de l'application, il permet de visualiser la liste des chansons téléchargées
 * @param navController : NavController, le contrôleur de navigation (necessaire pour songGridCard)
 * @param downloadFilesSong : (ID, ErrorViewModel, MutableState<List<Song>>) -> Unit, la fonction de téléchargement des paroles et du ficher audio d'une musique (nécessaire pour SongGridCard)
 * @param setPlayingTrue : (Boolean) -> Unit, la fonction qui met à jour l'état de lecture lorsqu'une musique est lancée (nécessaire pour SongGridCard)
 * @param deleteFiles : (Context, String, ID, MutableState<List<Song>>) -> Unit, la fonction de suppression des fichiers audio et des paroles d'une musique (nécessaire pour SongGridCard)
 * @param quitApplication : () -> Unit, la fonction de fermeture de l'application
 * @param downloadPlaylist : (MutableState<List<Song>>) -> Unit, la fonction de téléchargement de la playlist
 * @param errorViewModel : ErrorViewModel, le viewModel qui gère les erreurs de l'application (nécessaire pour downloadFilesSong)
 * @param filterViewModel : FilterViewModel, le viewModel qui gère les filtres de l'application (nécessaire pour le FilterText)
 * @param themeViewModel : ThemeViewModel, le viewModel qui gère les thèmes de l'application (nécessaire pour le ThemeSelector)
 * @param currentThemeIndex : Int, l'index du thème actuel (nécessaire pour le ThemeSelector)
 */
@Composable
fun MenuScreen(
    navController: NavController,
    downloadFilesSong: (fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.ID, ErrorViewModel, MutableState<List<fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song>>) -> Unit,
    setPlayingTrue: (Boolean) -> Unit,
    deleteFiles: (Context, String, fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.ID, MutableState<List<fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song>>) -> Unit,
    quitApplication : () -> Unit,
    downloadPlaylist : (MutableState<List<fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song>>) -> Unit,
    errorViewModel: ErrorViewModel,
    filterViewModel: FilterViewModel,
    themeViewModel: ThemeViewModel,
    currentThemeIndex: Int
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val screenHeight = constraints.maxHeight
        val songList = remember { mutableStateOf(Playlist.songs.toList()) }
        //On vérifie si l'object playlist est vide
        if (Playlist.songs.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85f)
            )
            {
                LaunchScreen(downloadPlaylist,songList)
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
            ) {
                SongGridCard(
                    modifier = Modifier
                        .fillMaxWidth(),
                    downloadFilesSong = downloadFilesSong,
                    setPlayingTrue = setPlayingTrue,
                    deleteFiles = deleteFiles,
                    navController = navController,
                    errorViewModel = errorViewModel,
                    songList = songList
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.15f)
                    .offset(y = (screenHeight * 0.7f).pxToDp())
            ) {
                FilterText(filterViewModel, modifier = Modifier.align(Alignment.Center), songList = songList)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.15f)
                .offset(y = (screenHeight * 0.85f).pxToDp())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ActionButton(
                    icon = Icons.Filled.CloudDownload,
                    contentDescription = "getPlaylist",
                    onClick = {downloadPlaylist(songList)},

                )
                ThemeSelector(currentThemeIndex) { newIndex ->
                    themeViewModel.setTheme(newIndex)
                }
                ActionButton(
                    icon = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Pause",
                    onClick = { quitApplication()},
                )
            }
        }
    }

}
