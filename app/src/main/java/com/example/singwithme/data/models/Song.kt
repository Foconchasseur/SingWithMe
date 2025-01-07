package com.example.singwithme.data.models

data class Song(
    val id: ID,
    var locked: Boolean,
    val path: String,
    var downloaded: Boolean,
)
{
    fun isLocked(): Boolean {
        return locked
    }

}