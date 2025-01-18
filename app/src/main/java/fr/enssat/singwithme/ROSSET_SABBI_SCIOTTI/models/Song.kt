package fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models

/**
 * Représente une chanson avec ses informations principales.
 *
 * Cette classe contient les informations relatives à une chanson, incluant son identifiant, son
 * état de verrouillage, son chemin d'accès, et son état de téléchargement. Elle permet de gérer
 * les chansons dans une playlist ou un autre contexte d'application.
 *
 * @property id L'identifiant unique de la chanson, représenté par un objet [ID].
 * @property locked Indique si la chanson est verrouillée (ne peut pas être modifiée ou supprimée).
 * @property path Le chemin d'accès à la chanson sur le système de fichiers.
 * @property downloaded Indique si la chanson a été téléchargée (true si téléchargée, false sinon).
 */
data class Song(
    val id: fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.ID,
    var locked: Boolean,
    val path: String,
    var downloaded: Boolean,
)
