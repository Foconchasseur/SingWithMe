package com.example.singwithme.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.singwithme.ui.pxToDp
import kotlin.math.roundToInt

/**
 * KaraokeSimpleText est un composant qui affiche le texte de la chanson en cours de lecture ainsi que la ligne précedente et suivante
 * @param mainText : String, la ligne de la chanson en cours de lecture
 * @param nextText : String, la ligne suivante de la chanson
 * @param lastText : String, la ligne précédente de la chanson
 * @param progress : Float, l'avancement de la ligne dans la chanson
 * @param modifier : Modifier, le modifier du composant
 */
@Composable
fun KaraokeSimpleText(
    mainText: String,
    nextText : String,
    lastText : String,
    progress: Float,
    modifier: Modifier
) {
    val fontsizeMainText = 30.sp
    val fontsizeOtherTexts = 20.sp

    // Variables pour la position du texte et sa taille utile pour le curseur et le texte partiel affiché
    var textWidth by remember { mutableStateOf(0) }
    var textHeight by remember { mutableStateOf(0) }
    var textPosition by remember { mutableStateOf(Offset.Zero) }
    Box(
        modifier = modifier,
    ){
        if (!mainText.isEmpty()) {
            Cursor(
                modifier = Modifier.align(Alignment.Center),
                textPosition, textWidth.pxToDp(), progress, fontsizeMainText.value.dp
            )
        }
        Text(mainText,
            fontSize = fontsizeMainText,
            modifier = Modifier
                .onSizeChanged { size ->
                    textWidth = size.width
                    textHeight = size.height
                }
                .align(Alignment.Center)
                .onGloballyPositioned { coordinates ->
                    val position = coordinates.positionInParent()
                    textPosition = position
                }
        )
        Text(
            nextText,
            fontSize = fontsizeOtherTexts,

            modifier = Modifier
                .offset(y = (textPosition.y - textHeight).dp)
                .align(Alignment.Center),
            color = MaterialTheme.colorScheme.tertiary
        )
        Text(
            lastText,
            fontSize = fontsizeOtherTexts,
            modifier = Modifier
                .offset(y = (textPosition.y + textHeight).dp)
                .align(Alignment.Center),
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(mainText,
            fontSize = fontsizeMainText,
            softWrap = false,
            modifier = Modifier
                .offset { IntOffset(textPosition.x.roundToInt(), textPosition.y.roundToInt()) }
                //Calcul de taille du texte à afficher selon l'avanacement de la phrase dans la chanson
                .size(width = textWidth.pxToDp() * progress, height = textHeight.pxToDp()),
            color = MaterialTheme.colorScheme.tertiary

        )
    }
}
