package com.example.singwithme.data.models

import java.io.Serializable

/**
 * Représente une ligne de texte des paroles d'une chanson avec un temps de début et un temps de fin.
 *
 * Cette classe est utilisée pour stocker les informations d'une ligne de texte de paroles, ainsi que
 * les moments où cette ligne doit apparaître et disparaître pendant la lecture de la chanson.
 *
 * @property text Le texte des paroles de la ligne.
 * @property startTime Le temps (en secondes) auquel la ligne de texte doit commencer à être affichée.
 * @property endTime Le temps (en secondes) auquel la ligne de texte doit disparaître.
 *
 * La classe implémente [Serializable] pour permettre la sérialisation et la désérialisation des données,
 * ce qui est utile pour le stockage ou le transfert des paroles.
 */
data class LyricsLine(
    var text: String = "",
    var startTime: Float = 0.0f,
    var endTime: Float = 0.0f
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}
