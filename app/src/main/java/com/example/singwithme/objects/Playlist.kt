package com.example.singwithme.objects

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.singwithme.data.models.ID
import com.example.singwithme.data.models.Song
import com.google.gson.Gson
import java.io.File

object Playlist {

    var songs: SnapshotStateList<Song> = mutableStateListOf<Song>()
    lateinit var cacheFile : File

    fun getSongById(id: ID): Song? {
        return songs.find { it.id == id }
    }

    fun updateDownloadedById(id: ID, update: Boolean, savedFile: Boolean) {
        val songIndex = songs.indexOfFirst { it.id == id }
        if (songIndex != -1) { // Vérifie si l'index existe
            songs[songIndex] = songs[songIndex].copy(downloaded = update)
        }
        if (savedFile) {
            saveMusicDataToCache(cacheFile)
        }
    }

    fun updateLockedById(id: ID, update: Boolean, savedFile: Boolean) {
        val songIndex = songs.indexOfFirst { it.id == id }
        if (songIndex != -1) { // Vérifie si l'index existe
            songs[songIndex] = songs[songIndex].copy(locked = update)
        }
        if (savedFile) {
            saveMusicDataToCache(cacheFile)
        }
    }




    private fun saveMusicDataToCache(cacheFile : File) {
        val json = Gson().toJson(songs)
        cacheFile.outputStream().use { outputStream ->
            outputStream.write(json.toByteArray())
        }
    }


}
