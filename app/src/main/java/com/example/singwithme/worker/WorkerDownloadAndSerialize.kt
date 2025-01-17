package com.example.singwithme.back

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.example.singwithme.objects.Constants
import com.example.singwithme.data.models.LyricsLine
import com.example.singwithme.data.models.SongData
import com.example.singwithme.viewmodel.ErrorViewModel
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream

class WorkerDownloadAndSerialize(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Log.i("WorkerDownloadAndSerialize", "Started Worker")
        val filename = inputData.getString("fileName") ?: return Result.failure()

        // Télécharger le fichier .md
        Log.d("URL DOWNLOAD","/{$filename}.md")
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Constants.PLAYLIST_URL + "/$filename.md")
            .build()

        return try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val markdown = response.body?.string()
                if (markdown != null) {
                    // Convertir les données Markdown en objet Music
                    val music = parseMarkdownToMusic(markdown)
                    val fileSaveName = filename.replace("/","_")
                    // Sérialiser directement l'objet Music
                    serializeObjectToCache(applicationContext, music, "${fileSaveName}.ser")

                    Log.i("WorkerDownloadAndSerialize", "Successfully serialized ${fileSaveName}")
                    return Result.success()
                }
            }

            // Si le téléchargement a échoué, on renvoie un résultat d'échec qui sera traiter par le TaskManager
            Log.e("WorkerDownloadAndSerialize", "Failed to download .md file")
            val errorData = Data.Builder()
                .putString("error", "erreur lors du traitement de la musique"
                )
                .build()
            Result.failure(errorData)

        } catch (e: Exception) {
                // Si il y a une levée d'exception, on renvoie un résultat d'échec qui sera traiter par le TaskManager
                Log.e("WorkerDownloadAndSerialize", "Error during execution : ${e.message}")
                val errorData = Data.Builder()
                    .putString("error",
                        "erreur lors du téléchargement de la musique"
                    )
                    .build()
                Result.failure(errorData)
        }
    }

    private fun parseMarkdownToMusic(markdown: String): SongData {
        val lines = markdown.split("\n")
        val title = lines[2]
        val artist = lines[4]
        val track = lines[6]

        val lyrics = mutableListOf<LyricsLine>()
        val regex = Regex("""\{ (\d+:\d+) \}([^{}]*)""")

        for (i in 8 until lines.size - 1) {
            val line = lines[i]

            val matchResult = regex.findAll(line)
            if (matchResult != null) {
                for (match in matchResult) {
                    val (stringStartTime, lyricsText) = match.destructured
                    val startTimeMinutes = stringStartTime.split(":")[0].toFloat()
                    val startTimeSeconds = stringStartTime.split(":")[1].toFloat()
                    val startTime = startTimeMinutes * 60 + startTimeSeconds

                    lyrics.add(LyricsLine(lyricsText, startTime))
                }
            }
        }

        if (lyrics.isEmpty()) {
            // Si aucune parole n'a été trouvée, on renvoie une exception
            throw Exception("Pas de paroles trouvées")
        }

        for (i in 0 until lyrics.size - 1) {
            lyrics[i].endTime = lyrics[i + 1].startTime
        }

        if (lyrics[0].startTime != 0.0f) {
            lyrics.add(0, LyricsLine("", 0.0f, lyrics[0].startTime))
        }

        return SongData(title, artist, lyrics, track)
    }

    private fun serializeObjectToCache(context: Context, songData: SongData, fileName: String) {
        val cacheDir = context.cacheDir
        val file = File(cacheDir, fileName)
        try {
            FileOutputStream(file).use { fos ->
                ObjectOutputStream(fos).use { oos ->
                    oos.writeObject(songData)
                }
            }
            Log.i("serializeObjectToCache", "Serialized object saved as $fileName")
        }
        catch (e: Exception) {
            Log.i("serializeObjectToCache", "Error during serialization : ${e.message}")
            throw Exception("Erreur lors de la sérialisation : \n ${e.message}")
        }

    }
}
