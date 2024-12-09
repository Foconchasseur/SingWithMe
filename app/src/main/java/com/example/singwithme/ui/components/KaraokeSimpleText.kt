package com.example.singwithme.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
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
import kotlin.math.roundToInt

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }


@Composable
fun KaraokeSimpleText(text: String, progress: Float, modifier: Modifier) {
    var fontsize = 30.sp
    var textWidth by remember { mutableStateOf(0) }
    var textHeight by remember { mutableStateOf(0) }

    var textPosition by remember { mutableStateOf(Offset.Zero) }
    Box(
        modifier = modifier,
    ){
        Text(text,
            fontSize = fontsize,
            modifier = Modifier
                .onSizeChanged { size ->
                    textWidth = size.width
                    textHeight = size.height
                }
                .align(Alignment.Center) // Y librement géré ici
                .onGloballyPositioned { coordinates ->
                    val position = coordinates.positionInParent()
                    textPosition = position
                    Log.d("KaraokeSimpleText", "position.x = ${position.x}")
                }
            , color= Color.Red)
        Text(text,
            fontSize = fontsize,
            softWrap = false,
            modifier = Modifier
                .offset { IntOffset(textPosition.x.roundToInt(), textPosition.y.roundToInt()) }
                //.align(Alignment.CenterStart)
                .size(width = textWidth.pxToDp() * progress, height = textHeight.pxToDp())

        )
    }
}
