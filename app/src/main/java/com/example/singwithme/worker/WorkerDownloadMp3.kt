package com.example.singwithme.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.example.singwithme.objects.Constants
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream

/**
 * Un [CoroutineWorker] responsable du téléchargement d'un fichier MP3 depuis un serveur.
 *
 * Cette classe effectue les opérations suivantes :
 * 1. Télécharge un fichier MP3 depuis un serveur spécifié dans [Constants.SERVER_URL].
 * 2. Enregistre le fichier téléchargé dans le répertoire de cache de l'application.
 * 3. Retourne un résultat de succès ou d'échec en fonction du statut du téléchargement.
 *
 * En cas d'erreur, un message d'erreur détaillé est inclus dans le résultat.
 */
class WorkerDownloadMp3(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    /**
     * Télécharge un fichier MP3 et l'enregistre dans le répertoire de cache de l'application.
     *
     * Cette méthode effectue les étapes suivantes :
     * - Récupère le nom du fichier MP3 à télécharger depuis les données d'entrée.
     * - Effectue une requête HTTP pour télécharger le fichier MP3 depuis l'URL spécifiée.
     * - Enregistre le fichier dans le répertoire de cache de l'application.
     * - Retourne [Result.success] si le téléchargement est réussi ou [Result.failure] en cas d'erreur.
     *
     * @return [Result.success] si l'opération réussit, [Result.failure] en cas d'erreur.
     */
    override suspend fun doWork(): Result {
        // Récupérer les paramètres
        val fileName = inputData.getString("fileName") ?: return Result.failure()
        val url = Constants.SERVER_URL + "/$fileName.mp3"
        Log.d("DownloadMp3Worker", "Téléchargement du fichier MP3 depuis : $url")

        try {
            // Initialiser OkHttpClient
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response: Response = client.newCall(request).execute()

            // Vérifier si le téléchargement a réussi
            if (response.isSuccessful) {
                response.body?.byteStream()?.use { inputStream ->
                    // Enregistrer le fichier dans le cache
                    val cacheDir = applicationContext.cacheDir
                    val fileSaveName = fileName.replace("/", "_")
                    val properFileName = if (fileSaveName.endsWith(".mp3")) fileSaveName else "$fileSaveName.mp3"

                    val file = File(cacheDir, properFileName)

                    FileOutputStream(file).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                    Log.d("DownloadMp3Worker", "Fichier MP3 enregistré avec succès : ${file.absolutePath}")
                }
                return Result.success()
            } else {
                Log.e("DownloadMp3Worker", "Échec du téléchargement. Code HTTP : ${response.code}")
                val errorData = Data.Builder()
                    .putString("error", "Il y a eu une erreur lors du téléchargement du fichier .MP3. " +
                            "Code HTTP : \n ${response.code}")
                    .build()
                return Result.failure(errorData)
            }
        } catch (e: Exception) {
            Log.e("DownloadMp3Worker", "Erreur pendant le téléchargement : ${e.message}")
            val errorData = Data.Builder()
                .putString("error", "Il y a eu une erreur lors du téléchargement du fichier .MP3")
                .build()
            return Result.failure(errorData)
        }
    }
}
