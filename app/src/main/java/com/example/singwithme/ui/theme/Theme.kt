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
    primary = LightPrimary, //Couleur des boutons et des swithcs
    secondary = LightSecondary, // Couleur du slider et des switchs
    background = LightBackground, // Couleur du fond de l'écran
    onBackground = LightOnBackground, // Couleur du texte
    tertiary = Color(0xFFFF0000) // Couleur du texte du karaoke
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

val JapanColorScheme = lightColorScheme(
    primary = Color(0xFFDE3163), // Rouge clair  pour les boutons et switchs
    secondary = Color(0xFFF3A5C6), // Rose sakura pour les sliders et switchs
    background = Color(0xFFFFF0F5), // Rose clair pour le fond de l'écran
    onBackground = Color(0xFF000000), // Vert classique pour le texte principal
    tertiary = Color(0xFFFFA07A) // Rouge pour le texte du karaoké
)




