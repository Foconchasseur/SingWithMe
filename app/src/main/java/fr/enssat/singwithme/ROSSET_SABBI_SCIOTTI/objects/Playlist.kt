package fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.objects

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.ID
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song
import com.google.gson.Gson
import java.io.File

/**
 * Objet responsable de la gestion de la playlist de musique.
 *
 * Cet objet permet de stocker, modifier et gérer la liste des chansons
 * dans la playlist. Il fournit également des méthodes pour mettre à jour
 * les informations des chansons (telles que `downloaded` et `locked`) et
 * pour sauvegarder ces données dans un fichier de cache.
 */
object Playlist {

    /**
     * Liste observable des chansons dans la playlist.
     * Elle contient des objets [Song] et est observée pour les changements dans l'UI.
     */
    var songs: SnapshotStateList<fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song> = mutableStateListOf<fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song>()

    /**
     * Fichier de cache utilisé pour sauvegarder et charger les données de la playlist.
     */
    lateinit var cacheFile: File

    /**
     * Récupère une chanson par son identifiant.
     *
     * @param id L'identifiant de la chanson à rechercher.
     * @return La chanson correspondante si trouvée, sinon `null`.
     */
    fun getSongById(id: fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.ID): fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song? {
        return songs.find { it.id == id }
    }

    /**
     * Met à jour l'état `downloaded` d'une chanson en fonction de son identifiant.
     * Si le fichier doit être sauvegardé, les données de la playlist sont sérialisées dans le cache.
     *
     * @param id L'identifiant de la chanson à mettre à jour.
     * @param update La nouvelle valeur de `downloaded`.
     * @param savedFile Indique si le fichier doit être sauvegardé.
     * @param songsList La liste des chansons mise à jour.
     */
    fun updateDownloadedById(
        id: fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.ID,
        update: Boolean,
        savedFile: Boolean,
        songsList: MutableState<List<fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song>>
    ) {
        val songIndex = songs.indexOfFirst { it.id == id }
        val songListIndex = songsList.value.indexOfFirst { it.id == id }
        if (songIndex != -1) { // Vérifie si l'index existe
            songs[songIndex] = songs[songIndex].copy(downloaded = update)
        }
        if (songListIndex != -1) {
            var songsListCopy = songsList.value.toMutableList()
            songsListCopy[songListIndex] = songsList.value[songListIndex].copy(downloaded = update)
            songsList.value = songsListCopy
        }
        if (savedFile) {
            saveMusicDataToCache(cacheFile)
        }
    }

    /**
     * Met à jour l'état `locked` d'une chanson en fonction de son identifiant.
     * Si le fichier doit être sauvegardé, les données de la playlist sont sérialisées dans le cache.
     *
     * @param id L'identifiant de la chanson à mettre à jour.
     * @param update La nouvelle valeur de `locked`.
     * @param savedFile Indique si le fichier doit être sauvegardé.
     * @param songsList La liste des chansons mise à jour.
     */
    fun updateLockedById(
        id: fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.ID,
        update: Boolean,
        savedFile: Boolean,
        songsList: MutableState<List<fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song>>
    ) {
        val songIndex = songs.indexOfFirst { it.id == id }
        val songListIndex = songsList.value.indexOfFirst { it.id == id }
        if (songIndex != -1) { // Vérifie si l'index existe
            songs[songIndex] = songs[songIndex].copy(locked = update)
        }
        if (songListIndex != -1) {
            var songsListCopy = songsList.value.toMutableList()
            songsListCopy[songListIndex] = songsList.value[songListIndex].copy(locked = update)
            songsList.value = songsListCopy
        }
        if (savedFile) {
            saveMusicDataToCache(cacheFile)
        }
    }

    /**
     * Sauvegarde les données de la playlist dans un fichier de cache au format JSON.
     *
     * @param cacheFile Le fichier dans lequel les données de la playlist seront sauvegardées.
     */
    private fun saveMusicDataToCache(cacheFile: File) {
        val json = Gson().toJson(songs)
        cacheFile.outputStream().use { outputStream ->
            outputStream.write(json.toByteArray())
        }
    }
}
