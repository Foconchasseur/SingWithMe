package com.example.singwithme.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.singwithme.ui.pxToDp
import kotlin.math.roundToInt



@Composable
fun KaraokeSimpleText(mainText: String, nextText : String, lastText : String, progress: Float, modifier: Modifier) {
    var fontsize = 30.sp
    var littleFontsize = 20.sp
    var textWidth by remember { mutableStateOf(0) }
    var textHeight by remember { mutableStateOf(0) }//mis à 100 pour la preview

    var textPosition by remember { mutableStateOf(Offset.Zero) }
    Box(
        modifier = modifier,
    ){
        if (!mainText.isEmpty()) {
            Cursor(
                modifier = Modifier.align(Alignment.Center),
                textPosition, textWidth.pxToDp(), progress, fontsize.value.dp
            )
        }
        Text(mainText,
            fontSize = fontsize,
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
            fontSize = littleFontsize,

            modifier = Modifier
                .offset(y = (textPosition.y - textHeight).dp)
                .align(Alignment.Center),
            color = MaterialTheme.colorScheme.tertiary
        )
        Text(
            lastText,
            fontSize = littleFontsize,
            modifier = Modifier
                .offset(y = (textPosition.y + textHeight).dp)
                .align(Alignment.Center),
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(mainText,
            fontSize = fontsize,
            softWrap = false,
            modifier = Modifier
                .offset { IntOffset(textPosition.x.roundToInt(), textPosition.y.roundToInt()) }
                .size(width = textWidth.pxToDp() * progress, height = textHeight.pxToDp()),
            color = MaterialTheme.colorScheme.tertiary

        )
    }
}
