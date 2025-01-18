package fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.objects.Constants
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.LyricsLine
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.SongData
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream

/**
 * Un [CoroutineWorker] responsable du téléchargement et de la sérialisation des données musicales.
 *
 * Cette classe effectue les opérations suivantes :
 * 1. Télécharge un fichier markdown contenant des informations sur la chanson depuis un serveur.
 * 2. Convertit le contenu du fichier markdown en un objet [SongData].
 * 3. Sérialise cet objet et le sauvegarde dans le cache local de l'application.
 *
 * La tâche peut échouer si le téléchargement ou la sérialisation rencontre une erreur.
 */
class WorkerDownloadAndSerialize(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    /**
     * Télécharge et sérialise une chanson à partir d'un fichier markdown.
     *
     * Cette méthode effectue les étapes suivantes :
     * - Récupère le nom du fichier markdown à télécharger depuis les données d'entrée.
     * - Télécharge le fichier markdown depuis l'URL spécifiée dans [Constants.SERVER_URL].
     * - Parse le contenu du fichier markdown pour en extraire les informations sur la chanson et ses paroles.
     * - Sérialise les données de la chanson en un objet [SongData] et les sauvegarde dans le cache de l'application.
     *
     * @return [Result.success] si l'opération réussit, [Result.failure] en cas d'erreur.
     */
    override suspend fun doWork(): Result {
        Log.i("WorkerDownloadAndSerialize", "Started Worker")
        val filename = inputData.getString("fileName") ?: return Result.failure()

        // Télécharger le fichier .md
        Log.d("URL DOWNLOAD", "/{$filename}.md")
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.objects.Constants.SERVER_URL + "/$filename.md")
            .build()

        return try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val markdown = response.body?.string()
                if (markdown != null) {
                    // Convertir les données Markdown en objet Music
                    val music = parseMarkdownToMusic(markdown)
                    val fileSaveName = filename.replace("/", "_")
                    // Sérialiser directement l'objet Music
                    serializeObjectToCache(applicationContext, music, "${fileSaveName}.ser")

                    Log.i("WorkerDownloadAndSerialize", "Successfully serialized ${fileSaveName}")
                    return Result.success()
                }
            }

            // Si le téléchargement a échoué, on renvoie un résultat d'échec
            Log.e("WorkerDownloadAndSerialize", "Failed to download .md file")
            val errorData = Data.Builder()
                .putString("error", "erreur lors du traitement de la musique")
                .build()
            Result.failure(errorData)

        } catch (e: Exception) {
            // Si une exception survient, on renvoie un résultat d'échec
            Log.e("WorkerDownloadAndSerialize", "Error during execution : ${e.message}")
            val errorData = Data.Builder()
                .putString("error", "erreur lors du téléchargement de la musique")
                .build()
            Result.failure(errorData)
        }
    }

    /**
     * Parse un contenu markdown pour extraire les informations sur la chanson et ses paroles.
     *
     * Le format du markdown est supposé être structuré de manière spécifique, avec des informations
     * sur le titre, l'artiste, la piste audio et les paroles avec leurs minutages.
     *
     * @param markdown Le contenu du fichier markdown à analyser.
     * @return Un objet [SongData] contenant les informations extraites.
     */
    private fun parseMarkdownToMusic(markdown: String): fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.SongData {
        val lines = markdown.split("\n")
        val title = lines[2]
        val artist = lines[4]
        val track = lines[6]

        val lyrics = mutableListOf<fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.LyricsLine>()
        val regex = Regex("""\{\s?(\d+:\d+)\s?\}([^{}]*)""")

        for (i in 8 until lines.size - 1) {
            val line = lines[i]
            val matchResult = regex.findAll(line)

            if (matchResult != null) {
                for (match in matchResult) {
                    val (stringStartTime, lyricsText) = match.destructured
                    val startTimeMinutes = stringStartTime.split(":")[0].toFloat()
                    val startTimeSeconds = stringStartTime.split(":")[1].toFloat()
                    val startTime = startTimeMinutes * 60 + startTimeSeconds

                    lyrics.add(
                        fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.LyricsLine(
                            lyricsText,
                            startTime
                        )
                    )
                }
            }
        }

        if (lyrics.isEmpty()) {
            throw Exception("Pas de paroles trouvées")
        }

        for (i in 0 until lyrics.size - 1) {
            if (lyrics[i].startTime == lyrics[i + 1].startTime) {
                lyrics[i + 1].startTime += 0.5f
            }
            lyrics[i].endTime = lyrics[i + 1].startTime
            if (lyrics[i].text == "") {
                lyrics[i].text = ". . ."
            }
            Log.d("start and end times", "${lyrics[i].startTime} and ${lyrics[i].endTime}")
        }

        if (lyrics[0].startTime != 0.0f) {
            lyrics.add(0,
                fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.LyricsLine(
                    ". . .",
                    0.0f,
                    lyrics[0].startTime
                )
            )
        }

        return fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.SongData(
            title,
            artist,
            lyrics,
            track
        )
    }

    /**
     * Sérialise un objet [SongData] et le sauvegarde dans le cache de l'application.
     *
     * @param context Le contexte de l'application utilisé pour accéder au répertoire de cache.
     * @param songData Les données de la chanson à sérialiser.
     * @param fileName Le nom du fichier de destination pour la sérialisation.
     */
    private fun serializeObjectToCache(context: Context, songData: fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.SongData, fileName: String) {
        val cacheDir = context.cacheDir
        val file = File(cacheDir, fileName)
        try {
            FileOutputStream(file).use { fos ->
                ObjectOutputStream(fos).use { oos ->
                    oos.writeObject(songData)
                }
            }
            Log.i("serializeObjectToCache", "Serialized object saved as $fileName")
        } catch (e: Exception) {
            Log.i("serializeObjectToCache", "Error during serialization : ${e.message}")
            throw Exception("Erreur lors de la sérialisation : \n ${e.message}")
        }
    }
}
