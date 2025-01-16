package com.example.singwithme.ui.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.singwithme.data.models.ID
import com.example.singwithme.data.models.Song
import com.example.singwithme.objects.Playlist
import com.example.singwithme.viewmodel.ErrorViewModel
import com.example.singwithme.viewmodel.FilterViewModel


@Composable
fun SongGridCard(
    modifier: Modifier = Modifier,
    downloadFunction: (ID, ErrorViewModel, MutableState<List<Song>>) -> Unit,
    setPlayingTrue: (Boolean) -> Unit,
    deleteFiles: (Context, String, ID, MutableState<List<Song>>) -> Unit,
    navController: NavController,
    errorViewModel: ErrorViewModel,
    songList: MutableState<List<Song>>
) {

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
                downloadFunction = downloadFunction,
                navController = navController,
                setPlayingTrue = setPlayingTrue,
                deleteFiles = deleteFiles,
                errorViewModel = errorViewModel,
                songList =  songList
                )
        }
    }
}

