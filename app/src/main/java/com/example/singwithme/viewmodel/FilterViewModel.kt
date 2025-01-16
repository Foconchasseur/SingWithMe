package com.example.singwithme.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.singwithme.data.models.Song
import com.example.singwithme.objects.Playlist

class FilterViewModel() : ViewModel() {

    var songs = mutableListOf<Song>()

    fun setFilteredSongs(text: String, unlocked : Boolean, downloaded: Boolean) : MutableList<Song> {
        this.songs = Playlist.songs.toList().filter { song ->
            if (!unlocked && !downloaded) {
                song.id.artist.contains(text, ignoreCase = true) || song.id.name.contains(text, ignoreCase = true)
            } else if (unlocked && !downloaded) {
                (song.id.artist.contains(text, ignoreCase = true) || song.id.name.contains(text, ignoreCase = true)) && !song.locked
            } else if (!unlocked) {
                (song.id.artist.contains(text, ignoreCase = true) || song.id.name.contains(text, ignoreCase = true)) && song.downloaded
            } else {
                (song.id.artist.contains(text, ignoreCase = true) || song.id.name.contains(text, ignoreCase = true)) && song.downloaded && !song.locked
            }
        }.toMutableList()
        return songs
    }

    fun getFilteredSongs(): List<Song> {
        return songs
    }

}