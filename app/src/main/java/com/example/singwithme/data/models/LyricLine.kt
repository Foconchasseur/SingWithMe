package com.example.singwithme.data.models

data class Lyric(val text: String, val startTime: Float, val endTime: Float)


fun timeToFloat(time: String): Float {
    val parts = time.split(":")
    return parts[0].toFloat() + parts[1].toFloat() / 60f
}
