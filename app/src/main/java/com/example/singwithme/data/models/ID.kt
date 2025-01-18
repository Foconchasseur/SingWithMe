package com.example.singwithme.data.models

/**
 * Représente une chanson avec un titre et un artiste.
 *
 * Cette classe permet de stocker les informations essentielles d'une chanson :
 * - [name] : le titre de la chanson.
 * - [artist] : le nom de l'artiste ou du groupe.
 *
 * La méthode [equals] est redéfinie pour comparer les instances de [ID] en fonction
 * des propriétés [name] et [artist]. Deux objets [ID] seront considérés comme égaux
 * si ces deux propriétés sont identiques.
 *
 * @property name Le titre de la chanson.
 * @property artist Le nom de l'artiste ou du groupe.
 */
data class ID(
    var name: String,
    var artist: String,
) {
    /**
     * Redéfinit la méthode [equals] pour vérifier si deux objets [ID] sont égaux.
     *
     * Cette comparaison est effectuée en fonction des propriétés [name] et [artist].
     *
     * @param other L'objet à comparer.
     * @return true si les objets sont égaux, sinon false.
     */
    override fun equals(other: Any?): Boolean {
        // Vérifie si l'objet est une instance de ID
        if (this === other) return true // Vérifie si c'est le même objet
        if (other !is ID) return false // Vérifie le type
        // Compare les propriétés pertinentes
        return this.name == other.name && this.artist == other.artist
    }
}
