package com.example.singwithme.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.singwithme.back.cache.Song
import com.example.singwithme.viewmodel.MusicViewModel


@Composable
fun MusicGridCard(
    playlist : List<Song>,
    modifier: Modifier = Modifier,
    downloadFunction: (String) -> Unit,
    isPlayingTrue: (Boolean) -> Unit,
    navController: NavController
) {
    Log.d("playlist", playlist.toString())
    LazyVerticalGrid(
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Top,
        columns = GridCells.Fixed(1),
        modifier = modifier.height(300.dp),
        state = rememberLazyGridState()
    ) {

        items(playlist) { item ->
            SongCard(item, downloadFunction = downloadFunction, navController = navController, isPlayingTrue = isPlayingTrue)
        }
    }
}

