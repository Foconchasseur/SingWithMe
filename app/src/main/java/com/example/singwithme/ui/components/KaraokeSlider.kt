package com.example.singwithme.ui.components

import KaraokeViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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

/**
 * KaraokeSlider est un composant qui affiche et permet de modifier la position de la lecture de la chanson
 * @param modifier : Modifier, le modifier du composant
 * @param karaokeViewModel : KaraokeViewModel, le viewModel qui gère ExoPlayer pour la lecture de la chanson
 * @param duration : Float, la durée de la chanson (endtime de la dernière ligne de la chanson en ms)
 */
@Composable
fun KaraokeSlider(
    modifier: Modifier,
    karaokeViewModel: KaraokeViewModel,
    duration: Float
){
    // On utilise le remember pour sauvegarder l'état du slider entre les recompositions
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    // Si la chanson est en cours de lecture, on met à jour la position du slider
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
                //Lorsque l'utilisateur change la position du slider, on met en pause la chanson jusqu'à ce qu'il relâche le slider
                onValueChange = {
                    if (karaokeViewModel.getIsPlaying()){
                        karaokeViewModel.pause()
                    }
                    sliderPosition = it
                },
                //Lorsque l'utilisateur relâche le slider, on met à jour la position de la chanson
                //Cela permet de ne pas mettre à jour la position de la chanson à chaque changement de position du slider et d'éviter trop d'appel sur Exoplayer
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
                //Affichage de la position du slider en minutes et secondes au lieu de millisecondes
                text = "${(sliderPosition / 60000).toInt()}m:${(sliderPosition / 1000  % 60).toInt().toString().padStart(2, '0')}s",
                fontSize = 25.sp
            )
        }
    }


}

