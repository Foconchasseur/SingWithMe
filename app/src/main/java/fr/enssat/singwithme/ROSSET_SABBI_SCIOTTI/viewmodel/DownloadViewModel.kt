package fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.ID
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.objects.Playlist
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.worker.TaskManager
import kotlinx.coroutines.launch
import java.io.File

/**
 * ViewModel responsable de la gestion des téléchargements et de la suppression des fichiers liés aux chansons.
 *
 * @param context Le contexte de l'application utilisé pour accéder au cache et initialiser les tâches.
 */
class DownloadViewModel(private val context: Context) : ViewModel() {
    private val taskManager = TaskManager(context)

    /**
     * Télécharge et sérialise les paroles d'une chanson ainsi que son fichier mp3 en arrière-plan.
     *
     * Cette méthode utilise le [TaskManager] pour gérer le téléchargement et la sérialisation
     * des données de la chanson. Elle met à jour la liste des chansons via l'état mutable passé en paramètre.
     *
     * @param id L'identifiant de la chanson à télécharger.
     * @param errorViewModel ViewModel pour gérer les erreurs rencontrées lors du téléchargement.
     * @param songsList Liste mutable des chansons à mettre à jour après le téléchargement.
     */
    fun downloadAndSerializeSong(id: fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.ID, errorViewModel: ErrorViewModel, songsList: MutableState<List<fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song>>) {
        viewModelScope.launch {
            taskManager.downloadAndSerializeMusic(id, errorViewModel, songsList)
        }
    }

    /**
     * Supprime les fichiers téléchargés associés à une chanson spécifique.
     *
     * Cette méthode vérifie l'existence des fichiers sérialisés et audio dans le cache local,
     * puis tente de les supprimer. Si la suppression réussit, elle met à jour l'état de téléchargement
     * de la chanson dans la liste.
     *
     * @param context Le contexte utilisé pour accéder au répertoire de cache.
     * @param fileName Le nom du fichier (sans extension) utilisé pour identifier les fichiers à supprimer.
     * @param id L'identifiant de la chanson dont les fichiers doivent être supprimés.
     * @param songsList Liste mutable des chansons à mettre à jour après la suppression.
     */
    fun deleteDownloadedFiles(context: Context, fileName: String, id: fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.ID, songsList: MutableState<List<fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song>>) {
        val file1 = File(context.cacheDir, "$fileName.ser")
        val file2 = File(context.cacheDir, "$fileName.mp3")
        if (file1.exists() && file2.exists()) {
            val deleted1 = file1.delete()
            val deleted2 = file2.delete()
            if (deleted1 && deleted2) {
                Playlist.updateDownloadedById(id, false, true, songsList)
                println("Les fichiers ont été supprimés du cache.")
                Log.i("DownLoadViewModel","Les fichiers ont été supprimés du cache")
            } else {
                Log.e("DownLoadViewModel","Erreur lors de la suppression des fichiers")
            }
        } else {
            Log.e("DownLoadViewModel","Les fichiers n'existent pas dans le cache.")
        }
    }
}
