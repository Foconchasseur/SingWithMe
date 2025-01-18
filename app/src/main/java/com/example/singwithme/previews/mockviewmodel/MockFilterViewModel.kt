package com.example.singwithme.previews.mockviewmodel

import com.example.singwithme.models.ID
import com.example.singwithme.viewmodel.FilterViewModel

import com.example.singwithme.models.Song
import com.example.singwithme.objects.Playlist

class MockFilterViewModel : FilterViewModel() {

    private var mockSongs = mutableListOf(
        Song(ID("Wonderwall - Remastered", "Oasis"), true, "/", false),
        Song(ID("Don't Look Back in Anger - Remastered", "Oasis"), false, "DontLookBack/DontLookBack.md", false),
        Song(ID("Stand by Me", "Oasis"), true, "/",false),
        Song(ID("Bohemian Rhapsody", "Queen"), false, "Bohemian/Bohemian.md",true),
        Song(ID("Love Me Like There's No Tomorrow - Special Edition", "Freddie Mercury"), true, "/",false)
    )

    override fun setFilteredSongs(text: String, unlocked: Boolean, downloaded: Boolean): MutableList<Song> {
        this.mockSongs = Playlist.songs.toList().filter { song ->
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
        return mockSongs
    }

    override fun getFilteredSongs(): List<Song> {
        return mockSongs
    }
}
