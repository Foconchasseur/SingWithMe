package com.example.singwithme.worker

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.await
import androidx.work.workDataOf
import com.example.singwithme.back.WorkerDownloadAndSerialize
import com.example.singwithme.objects.Playlist

class TaskManager (private val context: Context){

    private val workManager = WorkManager.getInstance(context)

    @SuppressLint("RestrictedApi")
    suspend fun downloadAndSerializeMusic(
        id: String,
    ) {
        try {
            val songPath = Playlist.getSongById(id)?.path?.substringBefore(".");
            Playlist.updateLockedById(id,true, false);
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

            // Observer les résultats
            // Observer la fin du travail
            workManager.getWorkInfoByIdLiveData(downloadLyricsRequest.id).observeForever { workInfo ->
                if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                    // Cela s'assure que le premier worker a bien terminé
                    workManager.getWorkInfoByIdLiveData(downloadMp3Request.id).observeForever { secondWorkInfo ->
                        if (secondWorkInfo.state == WorkInfo.State.SUCCEEDED) {
                            Log.d("Test", "Tous les travaux ont réussi")
                            Playlist.updateLockedById(id, false, true)
                            Playlist.updateDownloadedById(id, true, true)
                        } else if ((secondWorkInfo.state == WorkInfo.State.FAILED)) {
                            Log.d("Test", "Un ou plusieurs travaux ont échoué ou ont été annulés. État : ${secondWorkInfo.state}")
                            Playlist.updateLockedById(id, false, true)
                            Playlist.updateDownloadedById(id, false, true)
                        }
                    }
                } else if ((workInfo.state == WorkInfo.State.FAILED)) {
                    Log.d("Test", "Le premier travail a échoué. État : ${workInfo.state}")
                    Playlist.updateLockedById(id, false, true)
                    Playlist.updateDownloadedById(id, false, true)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}