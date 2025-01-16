package com.example.singwithme.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.singwithme.data.models.Song
import com.example.singwithme.viewmodel.FilterViewModel

@Composable
fun FilterText(filterViewModel: FilterViewModel, modifier: Modifier, songList: MutableState<List<Song>>) {
    var text by remember { mutableStateOf("") }
    //var forceRecompose by remember { mutableStateOf(0) }
    Row(
        modifier = modifier.fillMaxWidth()
    ){
        Box(
            modifier = Modifier
                .weight(0.8f) // 80% de la largeur
                .padding(horizontal = 8.dp)
        )  {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)), // Arrondir les coins
                value = text,
                onValueChange = {
                    text = it
                    songList.value = filterViewModel.setFilteredSongs(text)

                    //forceRecompose++
                },
                label = { Text("Artiste ou titre") }
            )
        }
        Box(
            modifier = Modifier
                .weight(0.2f) // 80% de la largeur
                .padding(horizontal = 8.dp)
        ){
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    text = ""
                    songList.value = filterViewModel.setFilteredSongs(text)
                }
            ){
                Text("Rénitialiser")
            }
        }

    }

}