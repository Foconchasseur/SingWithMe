package com.example.singwithme.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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
                x = ( ((textWidth.value * (progress-0.5))).dp), // /1.5 sinon ça ne marche pas correctement
                y = (textPosition.y.dp)
            )
            .size(5.dp, fontsize.value.dp) // Curseur mince et aligné à la taille du texte
            .background(color = Color.Gray.copy(alpha = 0.5f)) // Couleur grise translucide
    )
}