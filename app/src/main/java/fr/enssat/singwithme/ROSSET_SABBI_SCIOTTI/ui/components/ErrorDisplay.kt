package fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.viewmodel.ErrorViewModel

/**
 * ErrorDisplay est le composant qui affiche les erreurs au dessus de l'application
 * @param errorViewModel : ErrorViewModel, le viewModel qui gère les erreurs
 */
@Composable
fun ErrorDisplay(
    errorViewModel: ErrorViewModel
) {
    val errorMessage by errorViewModel.errorMessage.collectAsState() //écoute de l'erreur pour l'afficher

    if (errorMessage != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
                .zIndex(1f)
                // Capture toutes les interactions pour bloquer leur propagation et éviter l'utilisation du menu
                // Une musique peut être lancé en même temps qu'une erreur, le thread n'est pas coupé et ne pose aucun soucis
                .pointerInput(Unit) {
                    detectTapGestures { }
                },
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = errorMessage!!,
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    errorViewModel.clearError()
                    Log.i("ErrorDisplay", "Fermeture de la notification d'erreur ")

                }) {
                    Text("OK")
                }
            }
        }
    }
}

