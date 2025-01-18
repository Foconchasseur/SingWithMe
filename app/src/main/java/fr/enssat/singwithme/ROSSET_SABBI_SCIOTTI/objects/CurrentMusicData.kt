package fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.objects

import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.LyricsLine

/**
 * Contient les données actuelles de la musique en cours, y compris les paroles.
 *
 * @property lyrics La liste des lignes de paroles de la chanson en cours.
 */
object CurrentMusicData {
    var lyrics : List<fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.LyricsLine> = emptyList()
}
