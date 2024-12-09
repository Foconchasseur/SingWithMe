package com.example.singwithme.back.Lyrics

import java.io.Serializable

data class LyricsLine(
    var line: String = "",
    var timeStart: Float = 0.0f,
    var timeEnd: Float = 0.0f
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}