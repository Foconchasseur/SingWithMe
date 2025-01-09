package com.example.singwithme.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun LaunchScreen(

) {
    Text(
        text = "Welcome to SingWithMe",
        style = TextStyle(
            fontSize = 24.sp,
            color = Color.Black
        )
    )
}