package com.example.singwithme.data.models

data class ID (
    var name: String,
    var artist: String,
){
    override fun equals(other: Any?): Boolean {
        // Vérifie si l'objet est une instance de ID
        if (this === other) return true // Vérifie si c'est le même objet
        if (other !is ID) return false // Vérifie le type
        // Compare les propriétés pertinentes
        return this.name == other.name && this.artist == other.artist
    }
}