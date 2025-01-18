package com.example.singwithme.worker

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.await
import androidx.work.workDataOf
import com.example.singwithme.back.WorkerDownloadAndSerialize
import com.example.singwithme.data.models.ID
import com.example.singwithme.data.models.Song
import com.example.singwithme.objects.Playlist
import com.example.singwithme.viewmodel.ErrorViewModel

/**
* Classe qui gère les tâches de téléchargement et de sérialisation des musiques
 */
class TaskManager (private val context: Context){

    private val workManager = WorkManager.getInstance(context)

    /**
    * Fonction qui télécharge et sérialise une musique
    * @param id : ID de la musique à télécharger
     */
    @SuppressLint("RestrictedApi")
    suspend fun downloadAndSerializeMusic(
        id: ID,
        errorViewModel: ErrorViewModel,
        songList : MutableState<List<Song>>
    ) {
        // On passe par un try/catch pour gérer les exceptions
        try {
            // Récupérer le chemin de la musique
            val songPath = Playlist.getSongById(id)?.path?.substringBefore(".");
            Playlist.updateLockedById(id,true, false, songList);
            Log.d("Début download", "Démarrage du téléchargement de"+songPath)
            // Créer une chaîne de workers
            val downloadLyricsRequest = OneTimeWorkRequestBuilder<WorkerDownloadAndSerialize>()
                .setInputData(workDataOf(
                    "fileName" to songPath
                ))
                .build()

            val downloadMp3Request = OneTimeWorkRequestBuilder<WorkerDownloadMp3>()
                .setInputData(workDataOf(
                    "fileName" to songPath
                ))
                .build()

            // Exécuter les workers en chaîne
            workManager
                .beginWith(downloadLyricsRequest)
                .then(downloadMp3Request)
                .enqueue()

            // Observer la fin du travail
            workManager.getWorkInfoByIdLiveData(downloadLyricsRequest.id).observeForever { workInfo ->
                if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                    // Cela s'assure que le premier worker a bien terminé
                    workManager.getWorkInfoByIdLiveData(downloadMp3Request.id).observeForever { secondWorkInfo ->
                        if (secondWorkInfo.state == WorkInfo.State.SUCCEEDED) {
                            Log.d("Test", "Tous les travaux ont réussi")
                            Playlist.updateLockedById(id, false, true, songList)
                            Playlist.updateDownloadedById(id, true, true, songList)
                        } else if ((secondWorkInfo.state == WorkInfo.State.FAILED)) {
                            Log.d("Test", "Un ou plusieurs travaux ont échoué ou ont été annulés. État : ${secondWorkInfo.state}")
                            // Si le deuxième Worker à echoué, on affiche l'erreur
                            workInfo.outputData.getString("error")?.let { errorViewModel.showError(it) }
                            // Et on annule le téléchargement
                            Playlist.updateLockedById(id, false, true, songList)
                            Playlist.updateDownloadedById(id, false, true, songList)
                        }
                    }
                } else if ((workInfo.state == WorkInfo.State.FAILED)) {
                    Log.d("Test", "Le premier travail a échoué. État : ${workInfo.state}")
                    // Si le premier Worker à echoué, on affiche l'erreur
                    workInfo.outputData.getString("error")?.let { errorViewModel.showError(it) }
                    // Et on annule le téléchargement
                    Playlist.updateLockedById(id, false, true, songList)
                    Playlist.updateDownloadedById(id, false, true, songList)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}