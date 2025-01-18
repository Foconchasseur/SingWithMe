package fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.viewmodel

import androidx.lifecycle.ViewModel
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.objects.Playlist

/**
 * ViewModel utilisé pour filtrer et gérer une liste de chansons basée sur différents critères.
 *
 * Cette classe extrait et stocke une liste filtrée de chansons depuis la playlist globale.
 */
open class FilterViewModel() : ViewModel() {

    /**
     * Liste des chansons filtrées.
     *
     * Cette liste est mise à jour en fonction des critères fournis via [setFilteredSongs].
     */
    var songs = mutableListOf<fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song>()

    /**
     * Filtre les chansons de la playlist en fonction du texte, de l'état de verrouillage et du statut de téléchargement.
     *
     * @param text Le texte à rechercher dans le nom ou l'artiste de la chanson.
     * @param unlocked Si `true`, inclut uniquement les chansons non verrouillées.
     * @param downloaded Si `true`, inclut uniquement les chansons déjà téléchargées.
     * @return Une liste mutable des chansons correspondant aux critères.
     */
    open fun setFilteredSongs(text: String, unlocked: Boolean, downloaded: Boolean): MutableList<fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song> {
        this.songs = Playlist.songs.toList().filter { song ->
            if (!unlocked && !downloaded) {
                song.id.artist.contains(text, ignoreCase = true) || song.id.name.contains(text, ignoreCase = true)
            } else if (unlocked && !downloaded) {
                (song.id.artist.contains(text, ignoreCase = true) || song.id.name.contains(text, ignoreCase = true)) && !song.locked
            } else if (!unlocked) {
                (song.id.artist.contains(text, ignoreCase = true) || song.id.name.contains(text, ignoreCase = true)) && song.downloaded
            } else {
                (song.id.artist.contains(text, ignoreCase = true) || song.id.name.contains(text, ignoreCase = true)) && song.downloaded && !song.locked
            }
        }.toMutableList()
        return songs
    }

    /**
     * Récupère la liste actuelle des chansons filtrées.
     *
     * @return Une liste contenant les chansons correspondant au dernier filtrage appliqué.
     */
    open fun getFilteredSongs(): List<fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song> {
        return songs
    }
}
