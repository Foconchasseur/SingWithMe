package fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

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




