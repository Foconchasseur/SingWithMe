package com.example.singwithme.ui.theme

import CustomBackground
import CustomOnBackground
import CustomPrimary
import CustomSecondary
import DarkBackground
import DarkOnBackground
import DarkPrimary
import DarkSecondary
import LightBackground
import LightOnBackground
import LightPrimary
import LightSecondary
import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
// Définir des ColorScheme
val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    secondary = LightSecondary,
    background = LightBackground,
    onBackground = LightOnBackground,
    tertiary = Color(0xFFFF0000)
)

val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    secondary = DarkSecondary,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    tertiary = Color(0xFFFF00FF)
)

val CustomColorScheme = lightColorScheme(
    primary = CustomPrimary,
    secondary = CustomSecondary,
    background = CustomBackground,
    onBackground = CustomOnBackground,
    tertiary = Color(0xFF0000FF)
)




