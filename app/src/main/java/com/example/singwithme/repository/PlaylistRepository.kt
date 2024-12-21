package com.example.singwithme.repository

import android.content.Context
import android.util.Log
import com.example.singwithme.objects.Constants
import com.example.singwithme.data.models.Song
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.util.UUID

class PlaylistRepository(private val context: Context) {
    private val cacheFile = File(context.cacheDir, "playlist.json")

    // URL du fichier JSON à télécharger
    private val musicJsonUrl = Constants.PLAYLIST_URL+"/playlist.json"

    // Charger la liste depuis le cache ou télécharger le fichier si nécessaire
    suspend fun getMusicData(): List<Song> {
        val songs = if (cacheFile.exists()) {
            // Si le fichier est en cache, le lire
            readMusicDataFromCache()
        } else {
            // Sinon, télécharger le fichier et le mettre en cache
            downloadAndCacheMusicData()
        }

        return songs
    }

    // Lire les données du cache
    private fun readMusicDataFromCache(): List<Song> {
        cacheFile.inputStream().use { inputStream ->
            val json = inputStream.bufferedReader().use { it.readText() }
            return deserializeMusicData(json)
        }
    }

    // Désérialiser le JSON en une liste d'objets Song
    private fun deserializeMusicData(json: String): List<Song> {
        val type = object : TypeToken<List<Song>>() {}.type
        val songs = Gson().fromJson<List<Song>>(json, type)

        // Ajouter un ID unique à chaque chanson si manquant
        songs.forEach { song ->
            if (song.id == null) {
                song.id = UUID.randomUUID().toString()
            }
        }

        return songs
    }

    // Télécharger le fichier et mettre en cache
    private suspend fun downloadAndCacheMusicData(): List<Song> {
        return withContext(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val request = Request.Builder().url(musicJsonUrl).build()
                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    // Télécharger le JSON
                    val json = response.body?.string().orEmpty()
                    // Sauvegarder le fichier dans le cache
                    saveMusicDataToCache(json)
                    // Désérialiser et retourner la liste des musiques
                    deserializeMusicData(json)
                } else {
                    Log.e("MusicRepository", "Failed to download music data")
                    emptyList<Song>()
                }
            } catch (e: Exception) {
                Log.e("MusicRepository", "Error downloading music data", e)
                emptyList<Song>()
            }
        }
    }

    // Sauvegarder les données téléchargées dans le cache
    private fun saveMusicDataToCache(json: String) {
        cacheFile.outputStream().use { outputStream ->
            outputStream.write(json.toByteArray())
        }
    }

    fun getCacheFile(): File {
        return cacheFile
    }
}