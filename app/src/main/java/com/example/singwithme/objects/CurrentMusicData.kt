package com.example.singwithme.objects

import com.example.singwithme.models.LyricsLine

/**
 * Contient les données actuelles de la musique en cours, y compris les paroles.
 *
 * @property lyrics La liste des lignes de paroles de la chanson en cours.
 */
object CurrentMusicData {
    var lyrics : List<LyricsLine> = emptyList()
}
