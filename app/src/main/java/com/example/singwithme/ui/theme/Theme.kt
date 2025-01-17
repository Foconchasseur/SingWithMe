package com.example.singwithme.ui.theme

import BlueBackground
import BlueOnBackground
import BluePrimary
import BlueSecondary
import BlueTertiary
import DarkBackground
import DarkOnBackground
import DarkPrimary
import DarkSecondary
import DarkTertiary
import JapanBackground
import JapanOnBackground
import JapanPrimary
import JapanSecondary
import JapanTertiary
import LightBackground
import LightOnBackground
import LightPrimary
import LightSecondary
import LightTertiary
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Définir des ColorScheme
//Modifier themesName dans ThemeSelector.kt lors de l'ajout ou la suppression de thèmes
//Modifier themes dans KaraokeNavigation.kt lors de l'ajout ou la suppression de thèmes

val LightColorScheme = lightColorScheme(
    primary = LightPrimary, //Couleur des boutons et des swithcs
    secondary = LightSecondary, // Couleur du slider et des switchs
    background = LightBackground, // Couleur du fond de l'écran
    onBackground = LightOnBackground, // Couleur du texte
    tertiary = LightTertiary // Couleur du texte du karaoke
)

val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    secondary = DarkSecondary,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    tertiary = DarkTertiary
)

val BlueColorScheme = lightColorScheme(
    primary = BluePrimary,
    secondary = BlueSecondary,
    background = BlueBackground,
    onBackground = BlueOnBackground,
    tertiary = BlueTertiary
)

val JapanColorScheme = lightColorScheme(
    primary = JapanPrimary,
    secondary = JapanSecondary,
    background = JapanBackground,
    onBackground = JapanOnBackground,
    tertiary = JapanTertiary
)




