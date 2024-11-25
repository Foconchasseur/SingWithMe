package com.example.singwithme.back.cache

data class Song(
    val name: String,
    val artist: String,
    val locked: Boolean,
    val path: String,
)
{
    fun isLocked(): Boolean {
        return locked
    }
}