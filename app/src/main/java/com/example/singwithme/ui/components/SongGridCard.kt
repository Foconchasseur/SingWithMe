package com.example.singwithme.ui.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.singwithme.models.ID
import com.example.singwithme.models.Song
import com.example.singwithme.viewmodel.ErrorViewModel

/**
 * SongGridCard est un composant permettant d'afficher une liste de musique
 * @param modifier : Modifier, le modifier du composant
 * @param downloadFilesSong : (ID, ErrorViewModel, MutableState<List<Song>>) -> Unit, la fonction de téléchargement des paroles et du ficher audio d'une musique
 * @param setPlayingTrue : (Boolean) -> Unit, la fonction qui met à jour l'état de lecture lorsqu'une musique est lancée
 * @param deleteFiles : (Context, String, ID, MutableState<List<Song>>) -> Unit, la fonction de suppression des fichiers audio et des paroles d'une musique
 * @param navController : NavController, le contrôleur de navigation afin de lancer l'écran de lecture de la musique
 * @param errorViewModel : ErrorViewModel, le viewModel qui gère les erreurs de l'application (nécessaire pour downloadFilesSong)
 * @param songList : MutableState<List<Song>>, la liste des chansons filtrées (nécessaire pour downloadFilesSong et deleteFiles)
 *
 */
@Composable
fun SongGridCard(
    modifier: Modifier = Modifier,
    downloadFilesSong: (ID, ErrorViewModel, MutableState<List<Song>>) -> Unit,
    setPlayingTrue: (Boolean) -> Unit,
    deleteFiles: (Context, String, ID, MutableState<List<Song>>) -> Unit,
    navController: NavController,
    errorViewModel: ErrorViewModel,
    songList: MutableState<List<Song>>
) {
    //Création automatique d'une grille de chansons
    LazyVerticalGrid(
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Top,
        columns = GridCells.Fixed(1),
        modifier = modifier,
        state = rememberLazyGridState()
    ) {

        items(songList.value) { item ->
            SongCard(
                item,
                downloadFilesSong = downloadFilesSong,
                navController = navController,
                setPlayingTrue = setPlayingTrue,
                deleteFiles = deleteFiles,
                errorViewModel = errorViewModel,
                songList =  songList
                )
        }
    }
}

