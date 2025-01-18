package com.example.singwithme

import com.example.singwithme.viewmodel.KaraokeViewModel
import com.example.singwithme.repository.PlaylistRepository
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.singwithme.objects.Playlist
import com.example.singwithme.ui.KaraokeNavigation
import com.google.android.exoplayer2.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * L'activité principale de l'application Karaoke, responsable de la gestion de l'interface utilisateur
 * et de la logique de la lecture des chansons, de la gestion des playlists et des interactions avec le ViewModel.
 *
 * Cette activité :
 * - Initialise et gère la playlist via le [PlaylistRepository].
 * - Configure la vue pour afficher le karaoké en mode paysage.
 * - Gère la lecture et la mise en pause des chansons via le [KaraokeViewModel].
 *
 * @see KaraokeViewModel
 * @see PlaylistRepository
 */
class KaraokeActivity : ComponentActivity() {

    private lateinit var playlistRepository: PlaylistRepository

    private val karaokeViewModel: KaraokeViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory(application)
    }

    /**
     * Méthode appelée lors de la création de l'activité. Elle initialise la playlist et configure l'interface utilisateur.
     * Elle s'assure également que l'application fonctionne en mode paysage.
     *
     * @param savedInstanceState L'état précédent de l'activité, ou null si aucune donnée sauvegardée n'est disponible.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Forcer l'orientation en mode paysage
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE

        playlistRepository = PlaylistRepository(applicationContext)

        // Initialisation de la playlist et de son cache
        CoroutineScope(Dispatchers.Main).launch {
            playlistRepository.iniliazePlaylist()
            Playlist.cacheFile = playlistRepository.getCacheFile()

            setContent {
                // Affichage de la navigation du karaoké
                KaraokeNavigation(playlistRepository, karaokeViewModel)
            }
        }
    }

    /**
     * Méthode appelée lorsque l'activité passe en pause. Elle gère la mise en pause de la chanson si elle est en cours de lecture.
     */
    override fun onPause() {
        super.onPause()
        Log.d("KaraokeActivity", "onPause")
        if (karaokeViewModel.getIsPlaying()) {
            karaokeViewModel.pause()
        }
    }
    /**
     * Méthode appelée lorsqu'un utilisateur appuie sur le bouton de retour. Elle peut être modifiée pour personnaliser l'action du bouton retour.
     */
    override fun onBackPressed() {
        if (false) {
            super.onBackPressed()
        }
    }
}
