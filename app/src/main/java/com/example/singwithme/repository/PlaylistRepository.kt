package com.example.singwithme.repository

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.example.singwithme.objects.Constants
import com.example.singwithme.data.models.Song
import com.example.singwithme.objects.Playlist
import com.example.singwithme.viewmodel.ErrorViewModel
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class PlaylistRepository(private val context: Context) {
    private val cacheFile = File(context.cacheDir, "playlist.json")

    // URL du fichier JSON à télécharger
    private val musicJsonUrl = Constants.PLAYLIST_URL+"/playlist.json"

    fun iniliazePlaylist() {
        if (cacheFile.exists()) {
            Log.d("MusicRepository", "Reading music data from cache")
            readMusicDataFromCache()
        }
    }

    suspend fun downloadPlaylist(errorViewModel: ErrorViewModel, songsList: MutableState<List<Song>>) {
        if (!cacheFile.exists()) {
            Log.d("MusicRepository", "Downloading playlist")
            firstDownloadPlaylist(errorViewModel,songsList)
        } else {
            Log.d("MusicRepository", "Updating playlist")
            updatePlaylist(errorViewModel)
        }
    }

    private suspend fun firstDownloadPlaylist(errorViewModel: ErrorViewModel, songsList: MutableState<List<Song>>) {
        return withContext(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val request = Request.Builder().url(musicJsonUrl).build()
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    // Télécharger le JSON
                    val inputJson = response.body?.string().orEmpty()
                    val json = transformJsonArray(inputJson)
                    // Sauvegarder le fichier dans le cache
                    savePlaylistToCache(json)
                    // Désérialiser et retourner la liste des musiques
                    deserializeMusicData(json, songsList)
                } else {
                    errorViewModel.showError("Failed to download music data")
                    Log.e("MusicRepository", "Failed to download music data")
                }
            } catch (e: Exception) {
                errorViewModel.showError("Error downloading music data")
                Log.e("MusicRepository", "Error downloading music data", e)
            }
        }
    }

    private suspend fun updatePlaylist(errorViewModel: ErrorViewModel){
        return withContext(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val request = Request.Builder().url(musicJsonUrl).build()
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    // Télécharger le JSON
                    val inputJson = response.body?.string().orEmpty()
                    val json = transformJsonArray(inputJson)
                    val type = object : TypeToken<List<Song>>() {}.type
                    val newSongs = Gson().fromJson<List<Song>>(json, type)
                    for (newSong in newSongs) {
                        val songIndex = Playlist.songs.indexOfFirst { it.id == newSong.id }
                        Log.d("MusicRepository", "Song index: $songIndex")
                        if (songIndex == -1) {
                            Playlist.songs.add(newSong)
                        }
                    }
                    val updatedJson = Gson().toJson(Playlist.songs)
                    savePlaylistToCache(updatedJson)
                } else {
                    Log.e("MusicRepository", "Failed to download music data")
                    errorViewModel.showError("Failed to download music data")
                }
            } catch (e: Exception) {
                Log.e("MusicRepository", "Error downloading music data", e)
                errorViewModel.showError("Error downloading music data")
            }
        }
    }
    fun readMusicDataFromCache() {
        cacheFile.inputStream().use { inputStream ->
            val json = inputStream.bufferedReader().use { it.readText() }
            return deserializeMusicData(json, null)
        }
    }

    // Désérialiser le JSON en une liste d'objets Song
    private fun deserializeMusicData(json: String, songsList: MutableState<List<Song>>?) {
        val type = object : TypeToken<List<Song>>() {}.type
        val songs = Gson().fromJson<List<Song>>(json, type)
        Playlist.songs.clear()
        Playlist.songs.addAll(songs)
        if (songs != null) {
            if (songsList != null) {
                songsList.value = Playlist.songs.toList()
            }
        }
    }

    // Sauvegarder les données téléchargées dans le cache
    private fun savePlaylistToCache(json: String) {
        cacheFile.outputStream().use { outputStream ->
            outputStream.write(json.toByteArray())
        }
    }

    fun getCacheFile(): File {
        return cacheFile
    }

    /**
     * Transforme un tableau JSON en un autre tableau JSON pour correspondre à la structure de données que l'on souhaite utiliser
     */
    fun transformJsonArray(input: String): String {
        //TODO: Peut être gérer les erreurs ici aussi
        val originalArray = JSONArray(input)
        val transformedArray = JSONArray()

        for (i in 0 until originalArray.length()) {
            val originalObject = originalArray.getJSONObject(i)
            // Créer l'objet "id"
            val id = JSONObject()
            id.put("name", originalObject.getString("name"))
            id.put("artist", originalObject.getString("artist"))
            // Créer le nouvel objet transformé
            val transformedObject = JSONObject()
            transformedObject.put("id", id)
            val locked =  originalObject.optBoolean("locked", false)
            transformedObject.put("locked", locked)
            val path = originalObject.optString("path","")
            transformedObject.put("path", path)
            transformedObject.put("downloaded", false)

            // Ajouter au tableau transformé
            transformedArray.put(transformedObject)
        }

        return transformedArray.toString(4) // Beautifie la sortie avec une indentation
    }
}