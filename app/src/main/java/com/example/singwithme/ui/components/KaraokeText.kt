package com.example.singwithme.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.singwithme.data.models.LyricLine
import kotlinx.coroutines.delay

@Composable
fun KaraokeText(list: List<LyricLine>, modifier: Modifier) {

    // Variable pour suivre le temps de la chanson
    val progress = remember { mutableStateOf(0f) } // Progression de la musique
    val current = remember { mutableStateOf(0) } // Index de la ligne actuelle

    // Simuler le temps de la chanson (remplacer par le vrai temps de la musique)
    LaunchedEffect(Unit) {
        // Simuler l'écoulement du temps toutes les 100 ms (0.1 seconde)
        while (progress.value < 100f) {
            delay(100) // Attente de 100 ms
            progress.value += 0.1f // Incrémenter le temps
            if (progress.value >= 100f) {
                progress.value = 0f // Réinitialiser après 100 secondes (par exemple)
            }
        }
    }

    KaraokeSimpleText(list[current.value].text,progress.value, modifier = modifier)
}

