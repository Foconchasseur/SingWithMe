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

/**
 * Repository responsable de la gestion de la playlist.
 *
 * Cette classe gère le téléchargement, la mise à jour, la lecture et la transformation des données de la playlist.
 * Les données sont mises en cache pour une utilisation hors ligne.
 *
 * @param context Contexte utilisé pour accéder au cache et aux ressources.
 */
class PlaylistRepository(private val context: Context) {

    /** Fichier de cache pour stocker les données de la playlist. */
    private val cacheFile = File(context.cacheDir, "songplaylist.json")

    /** URL du fichier JSON à télécharger. */
    private val musicJsonUrl = Constants.SERVER_URL + "/playlist.json"

    /**
     * Initialise la playlist en lisant les données du cache si disponibles.
     */
    fun iniliazePlaylist() {
        if (cacheFile.exists()) {
            Log.d("MusicRepository", "Reading music data from cache")
            readMusicDataFromCache()
        }
    }

    /**
     * Télécharge ou met à jour la playlist depuis le serveur.
     *
     * @param errorViewModel ViewModel utilisé pour gérer les erreurs.
     * @param songsList État mutable contenant la liste des chansons.
     */
    suspend fun downloadPlaylist(errorViewModel: ErrorViewModel, songsList: MutableState<List<Song>>) {
        if (!cacheFile.exists()) {
            Log.d("MusicRepository", "Downloading playlist")
            firstDownloadPlaylist(errorViewModel, songsList)
        } else {
            Log.d("MusicRepository", "Updating playlist")
            updatePlaylist(errorViewModel)
        }
    }

    /**
     * Télécharge et enregistre pour la première fois la playlist depuis le serveur.
     *
     * @param errorViewModel ViewModel utilisé pour gérer les erreurs.
     * @param songsList État mutable contenant la liste des chansons.
     */
    private suspend fun firstDownloadPlaylist(errorViewModel: ErrorViewModel, songsList: MutableState<List<Song>>) {
        withContext(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val request = Request.Builder().url(musicJsonUrl).build()
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val inputJson = response.body?.string().orEmpty()
                    val json = transformJsonArray(inputJson)
                    savePlaylistToCache(json)
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

    /**
     * Met à jour la playlist en comparant avec les données actuelles et en téléchargeant les nouvelles chansons.
     *
     * @param errorViewModel ViewModel utilisé pour gérer les erreurs.
     */
    private suspend fun updatePlaylist(errorViewModel: ErrorViewModel) {
        withContext(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val request = Request.Builder().url(musicJsonUrl).build()
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val inputJson = response.body?.string().orEmpty()
                    val json = transformJsonArray(inputJson)
                    val type = object : TypeToken<List<Song>>() {}.type
                    val newSongs = Gson().fromJson<List<Song>>(json, type)
                    for (newSong in newSongs) {
                        val songIndex = Playlist.songs.indexOfFirst { it.id == newSong.id }
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

    /**
     * Lit les données de la playlist depuis le fichier de cache.
     */
    fun readMusicDataFromCache() {
        cacheFile.inputStream().use { inputStream ->
            val json = inputStream.bufferedReader().use { it.readText() }
            deserializeMusicData(json, null)
        }
    }

    /**
     * Désérialise le JSON en une liste de chansons.
     *
     * @param json Le contenu JSON à désérialiser.
     * @param songsList État mutable pour mettre à jour la liste des chansons (facultatif).
     */
    private fun deserializeMusicData(json: String, songsList: MutableState<List<Song>>?) {
        val type = object : TypeToken<List<Song>>() {}.type
        val songs = Gson().fromJson<List<Song>>(json, type)
        Playlist.songs.clear()
        Playlist.songs.addAll(songs)
        songsList?.value = Playlist.songs.toList()
    }

    /**
     * Sauvegarde les données de la playlist dans le cache.
     *
     * @param json Le contenu JSON à sauvegarder.
     */
    private fun savePlaylistToCache(json: String) {
        cacheFile.outputStream().use { outputStream ->
            outputStream.write(json.toByteArray())
        }
    }

    /**
     * Renvoie le fichier de cache contenant les données de la playlist.
     *
     * @return Le fichier de cache.
     */
    fun getCacheFile(): File {
        return cacheFile
    }

    /**
     * Transforme un tableau JSON en un format compatible avec la structure des chansons.
     *
     * @param input Le contenu JSON à transformer.
     * @return Une chaîne JSON transformée.
     */
    fun transformJsonArray(input: String): String {
        val originalArray = JSONArray(input)
        val transformedArray = JSONArray()

        for (i in 0 until originalArray.length()) {
            val originalObject = originalArray.getJSONObject(i)
            val id = JSONObject().apply {
                put("name", originalObject.getString("name"))
                put("artist", originalObject.getString("artist"))
            }
            val transformedObject = JSONObject().apply {
                put("id", id)
                put("locked", originalObject.optBoolean("locked", false))
                put("path", originalObject.optString("path", ""))
                put("downloaded", false)
            }
            transformedArray.put(transformedObject)
        }

        return transformedArray.toString(4) // Beautifie la sortie avec une indentation
    }
}
