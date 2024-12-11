package com.example.singwithme.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.singwithme.back.cache.Song


private val musicCardData = listOf(
    Song("booba","b2o",true, "/"),
)
@Composable
fun MusicGridCard(
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Top,
        columns = GridCells.Fixed(1),
        modifier = modifier.height(100.dp),
        state = rememberLazyGridState()
    ) {

        items(musicCardData) { item ->
            MusicCard(item)
        }
    }
}

