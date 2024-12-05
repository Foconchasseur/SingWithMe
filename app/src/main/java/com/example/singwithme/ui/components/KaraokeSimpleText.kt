package com.example.singwithme.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }


@Composable
fun KaraokeSimpleText(text: String, progress: Float, modifier: Modifier) {
    var textsize by remember { mutableStateOf(0) }
    Box(
        modifier = modifier
    ){
        Text(text,
            modifier = Modifier
                .onSizeChanged { size ->
                    textsize = size.width
                }
            , color= Color.Red)
        Text(text,
            softWrap = false,
            modifier = Modifier.size(textsize.pxToDp() * progress))
    }
}
