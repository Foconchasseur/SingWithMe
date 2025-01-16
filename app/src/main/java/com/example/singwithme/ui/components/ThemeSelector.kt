package com.example.singwithme.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@Composable
fun ThemeSelector(
    currentThemeIndex: Int,
    onThemeChanged: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth(0.6f).fillMaxHeight()
    ) {
        val themesName = listOf("clair", "sombre", "bleu","japonais") // Ajouter les noms des thèmes
        // Bouton de navigation gauche
        IconButton(
            onClick = {
            Log.d("ThemeSelector", "Current theme index: $currentThemeIndex" + " Fleche gauche")
            if (currentThemeIndex > 0) {
                onThemeChanged(currentThemeIndex - 1)
                Log.d("ThemeSelector", "New theme index: $currentThemeIndex" + " Fleche gauche")
            }},
            modifier = Modifier.weight(0.15f) // 15% de la largeur
        ) {
            if (currentThemeIndex != 0) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous Theme")
            }
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(0.7f) // 70% de la largeur*
        ){
            // Affichage du nom du thème actuel (optionnel)
            Text(
                text = "Thème ${themesName[currentThemeIndex]}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Center)
                )
        }

        // Bouton de navigation droite
        IconButton(onClick = {
            Log.d("ThemeSelector", "Current theme index: $currentThemeIndex" + " Fleche droite")
            if (currentThemeIndex < themesName.size - 1) {  // Changer cette limite en fonction du nombre de thèmes
                onThemeChanged(currentThemeIndex + 1)
                Log.d("ThemeSelector", "New theme index: $currentThemeIndex" + " Fleche droite")
            }},
            modifier = Modifier.weight(0.15f)
            ){
            if (currentThemeIndex != themesName.size - 1) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next Theme")
            }
        }
    }
}
