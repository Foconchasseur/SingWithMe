package com.example.singwithme.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.singwithme.data.models.Song
import com.example.singwithme.objects.Playlist

class FilterViewModel() : ViewModel() {

    var text = ""
    var songs = mutableListOf<Song>()
    fun setFilter(text: String) {
        this.text = text
    }

    fun getFilter(): String {
        return text
    }

    fun setFilteredSongs(text: String) : MutableList<Song> {
        if (text == "") {
            this.songs = Playlist.songs.toMutableList()
        } else {
            this.songs = Playlist.songs.toList().filter { song ->
                song.id.artist.contains(text, ignoreCase = true) || song.id.name.contains(text, ignoreCase = true)
            }.toMutableList()
        }
        return songs
    }

    fun getFilteredSongs(): List<Song> {
        return songs
    }

}