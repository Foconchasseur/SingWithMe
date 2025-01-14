package com.example.singwithme.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.singwithme.data.models.Song
import com.example.singwithme.viewmodel.FilterViewModel

@Composable
fun FilterText(filterViewModel: FilterViewModel, modifier: Modifier, songList: MutableState<List<Song>>) {
    var text by remember { mutableStateOf("") }
    //var forceRecompose by remember { mutableStateOf(0) }
    Row(modifier = modifier){
        TextField(

            value = text,
            onValueChange = {
                text = it
                songList.value = filterViewModel.setFilteredSongs(text)

                //forceRecompose++
            },
            label = { Text("Artiste ou titre") }
        )
        Button(
            onClick = {
                filterViewModel.setFilter(text)
                songList.value = filterViewModel.setFilteredSongs(text)
                Log.d("FilterText", "Filtering songs with text ${songList.value}")
            }
        ){
            Text("Filtrer")
        }
    }

}