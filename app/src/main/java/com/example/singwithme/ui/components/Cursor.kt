package com.example.singwithme.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Cursor est le composant qui représente le curseur de progression de la chanson
 * @param modifier : Modifier, le modifier du curseur
 * @param textPosition : Offset, la position du texte
 * @param textWidth : Dp, la largeur du texte
 * @param progress : Float, l'avancement de la phrase dans la chanson
 * @param fontsize : Dp, la taille de la police du texte
 */
@Composable
fun Cursor(
    modifier: Modifier,
    textPosition: Offset,
    textWidth: Dp,
    progress: Float,
    fontsize: Dp
) {
    Box(
        modifier = modifier
            .offset(
                x = ( ((textWidth.value * (progress-0.5))).dp), //Calcul du position du curseur selon la taille du texte et l'avancement de la phrase dans la chanson
                y = (textPosition.y.dp)
            )
            .size(5.dp, fontsize.value.dp) // Curseur mince en largeur et adapté en hauteur à la taille du texte
            .background(color = Color.Gray.copy(alpha = 0.5f))
    )
}