package com.example.singwithme.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton(
    icon: ImageVector,
    contentDescription: String,
    onClick:  () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = Color.Black,
    size: Dp = 48.dp
) {
    IconButton(
        onClick = {onClick()},
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.size(size)
        )
    }
}

