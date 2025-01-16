package com.example.singwithme.ui.components

import KaraokeViewModel
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun KaraokeSlider(modifier: Modifier, karaokeViewModel: KaraokeViewModel, duration: Float){
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    if(karaokeViewModel.getIsPlaying()){
        sliderPosition = karaokeViewModel.getCurrentPosition()?.toFloat() ?: 0f
    }
    Row(
        modifier = modifier.fillMaxWidth()

    ){
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.8f) // 80% de la largeur
                .padding(horizontal = 8.dp)
        )  {
            Slider(
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                value = sliderPosition,

                onValueChange = {
                    if (karaokeViewModel.getIsPlaying()){
                        karaokeViewModel.pause()
                    }
                    sliderPosition = it
                },
                onValueChangeFinished = {
                    karaokeViewModel.setCurrentPosition(sliderPosition.toLong())
                    karaokeViewModel.pause()
                },
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.secondary,
                    activeTrackColor = MaterialTheme.colorScheme.secondary,
                    inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                steps = duration.toInt() ,
                valueRange = 0f..duration * 1000
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.2f) //
                .padding(horizontal = 8.dp)
        ){
            Text(
                text = "${(sliderPosition / 60000).toInt()}m:${(sliderPosition / 1000  % 60).toInt().toString().padStart(2, '0')}s",
                fontSize = 25.sp
            )
        }
    }


}

