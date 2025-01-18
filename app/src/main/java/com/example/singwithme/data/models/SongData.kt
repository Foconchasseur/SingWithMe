package com.example.singwithme.data.models

import java.io.Serializable

/**
 * Représente les données d'une chanson, y compris son titre, son artiste, ses paroles et le chemin du fichier audio.
 *
 * Cette classe est utilisée pour stocker les informations complètes d'une chanson, y compris :
 * - Le titre de la chanson.
 * - Le nom de l'artiste ou du groupe.
 * - Les paroles de la chanson sous forme de liste de lignes ([LyricsLine]).
 * - Le chemin d'accès au fichier audio associé à la chanson.
 *
 * Cette classe implémente [Serializable] afin de permettre la sérialisation et la désérialisation des données,
 * ce qui est utile pour le stockage ou le transfert des informations sur la chanson.
 *
 * @property title Le titre de la chanson.
 * @property artist Le nom de l'artiste ou du groupe.
 * @property lyrics La liste des lignes de paroles de la chanson, chaque ligne étant un objet [LyricsLine].
 * @property trackPath Le chemin d'accès au fichier audio de la chanson.
 */
data class SongData(
    val title: String,
    val artist: String,
    val lyrics: List<LyricsLine>,
    val trackPath: String
) : Serializable
