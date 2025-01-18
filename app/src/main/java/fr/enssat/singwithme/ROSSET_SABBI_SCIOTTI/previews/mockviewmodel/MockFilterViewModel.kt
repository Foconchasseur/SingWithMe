package fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.previews.mockviewmodel

import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.ID
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.viewmodel.FilterViewModel

import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.objects.Playlist

class MockFilterViewModel : FilterViewModel() {

    private var mockSongs = mutableListOf(
        fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song(
            fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.ID(
                "Wonderwall - Remastered",
                "Oasis"
            ), true, "/", false
        ),
        fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song(
            fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.ID(
                "Don't Look Back in Anger - Remastered",
                "Oasis"
            ), false, "DontLookBack/DontLookBack.md", false
        ),
        fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song(
            fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.ID(
                "Stand by Me",
                "Oasis"
            ), true, "/", false
        ),
        fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song(
            fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.ID(
                "Bohemian Rhapsody",
                "Queen"
            ), false, "Bohemian/Bohemian.md", true
        ),
        fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song(
            fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.ID(
                "Love Me Like There's No Tomorrow - Special Edition",
                "Freddie Mercury"
            ), true, "/", false
        )
    )

    override fun setFilteredSongs(text: String, unlocked: Boolean, downloaded: Boolean): MutableList<fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song> {
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

    override fun getFilteredSongs(): List<fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song> {
        return mockSongs
    }
}
