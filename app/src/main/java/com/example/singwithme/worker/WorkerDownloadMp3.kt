package com.example.singwithme.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.singwithme.Constants
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream

class WorkerDownloadMp3 (context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        // Récupérer les paramètres
        val fileName = inputData.getString("fileName") ?: return Result.failure()
        val url = Constants.PLAYLIST_URL + "/$fileName.mp3"
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
                    val fileSaveName = fileName.replace("/","_")
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
                return Result.failure()
            }
        } catch (e: Exception) {
            Log.e("DownloadMp3Worker", "Erreur pendant le téléchargement : ${e.message}")
            return Result.failure()
        }
    }
}