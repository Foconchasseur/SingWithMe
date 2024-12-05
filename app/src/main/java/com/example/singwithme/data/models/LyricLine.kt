package com.example.singwithme.data.models

data class LyricLine(
    val text: String,
    val startTime: Float, // Temps de début en secondes
    val endTime: Float    // Temps de fin en secondes
)

fun parseLyrics(lyrics: String): List<LyricLine> {
    val regex = """\{ (\d+):(\d+) \}(.+?)""".toRegex()
    return regex.findAll(lyrics).map { matchResult ->
        val minutes = matchResult.groupValues[1].toInt()
        val seconds = matchResult.groupValues[2].toInt()
        val text = matchResult.groupValues[3].trim()
        val timeInSeconds = minutes * 60 + seconds
        LyricLine(text, timeInSeconds.toFloat(), timeInSeconds.toFloat()) // Ajuste les minutages si nécessaire
    }.toList()
}