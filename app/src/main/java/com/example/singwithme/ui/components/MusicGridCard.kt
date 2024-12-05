package com.example.singwithme.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.singwithme.data.models.Music

private val musicCardData = listOf(
    Music("booba","b2o","/",true),
    Music("Warriors","Imagine Dragons","/",true),
    Music("B","b2o","/",true),
    Music("Warriors","Imagine Dragons","/",true),
    Music("booba","b2o","/",true),
    Music("Warriors","Imagine Dragons","/",true)
)
@Composable
fun MusicGridCard(
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = modifier.height(100.dp),
        state = rememberLazyGridState()
    ) {

        items(musicCardData) { item ->
            MusicCard(item)
        }
    }
}

