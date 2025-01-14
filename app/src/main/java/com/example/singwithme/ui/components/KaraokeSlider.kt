package com.example.singwithme.ui.components

import KaraokeViewModel
import android.util.Log
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun KaraokeSlider(modifier: Modifier, karaokeViewModel: KaraokeViewModel, duration: Float){
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    sliderPosition = karaokeViewModel.getCurrentPosition()?.toFloat() ?: 0f
    if (sliderPosition != 0f) {
        sliderPosition /= 1000
    }
    Column {
        Slider(
            modifier = modifier
                .width(600.dp)
                .padding(40.dp),
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
                Log.d("sliderPosition", sliderPosition.toString())
                karaokeViewModel.setCurrentPosition((sliderPosition * 1000).toLong())
                            },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.secondary,
                activeTrackColor = MaterialTheme.colorScheme.secondary,
                inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            steps = duration.toInt(),
            valueRange = 0f..duration
        )
        Text(text = "${(sliderPosition / 60).toInt()}m:${(sliderPosition % 60).toInt().toString().padStart(2, '0')}s",
            fontSize = 40.sp)
    }
}

