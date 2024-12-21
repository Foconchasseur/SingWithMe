package com.example.singwithme.data.models

data class Song(
    val name: String,
    val artist: String,
    var locked: Boolean,
    val path: String,
    var downloaded: Boolean,
    var id: String? = null // Nouveau champ pour l'ID unique
)
{
    fun isLocked(): Boolean {
        return locked
    }

}