package com.example.singwithme.data.models

import java.io.Serializable

data class LyricsLine(
    var text: String = "",
    var startTime: Float = 0.0f,
    var endTime: Float = 0.0f
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}