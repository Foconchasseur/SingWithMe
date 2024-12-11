package com.example.singwithme.back.Lyrics

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.singwithme.data.models.LyricsLine
import com.example.singwithme.data.models.Music
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream

class WorkerMd2Lyrics (context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Log.e("WorkerMd2Lyrics", "Hello from WorkerMd2Lyrics")

        val markdown = loadMarkdownFileAsString("Bohemian.md")
        val music = parseMarkdownToLyrics(markdown)
        serializeObjectToCache(applicationContext, music, music.title+".ser")

        return Result.Success()

    }

    fun loadMarkdownFileAsString(filePath: String): String {
        val cacheDir = applicationContext.cacheDir
        val file = File(cacheDir, filePath)
        return file.readText()
    }

    fun parseMarkdownToLyrics(markdown: String): Music {
        val lines = markdown.split("\n")
        val title = lines[2]
        val artist = lines[4]
        val track = lines[6]

        val lyrics = mutableListOf<LyricsLine>()

        val regex = Regex("""\{ (\d+:\d+) \}([^{}]*)""")

        for (i in 8 until lines.size-1) {
            val line = lines[i]

            val matchResult = regex.findAll(line)

            if (matchResult != null) {
                for (match in matchResult) {
                    val (StringstartTime, lyricsText) = match.destructured
                    val startTimeMinutes = StringstartTime.split(":")[0].toFloat()
                    val startTimeSeconds = StringstartTime.split(":")[1].toFloat()

                    val startTime = startTimeMinutes * 60 + startTimeSeconds

                    val lyricsLine = LyricsLine(lyricsText, startTime)
                    Log.e("Lyrics for 8-size", "LyricsLine: $lyricsLine")
                    lyrics.add(lyricsLine)
                }
            }
        }


        for (i in 0 until lyrics.size-1) {
            lyrics[i].endTime=lyrics[i + 1].startTime
            val line = lyrics[i]
            Log.e("Lyrics", "LyricsLine: $line")
        }

        val music = Music(title, artist, lyrics, track)
        return music
    }

    fun serializeObjectToCache(context: Context, music: Music, fileName: String) {
        val cacheDir = context.cacheDir
        val file = File(cacheDir, fileName)
        val fileOutputStream = FileOutputStream(file)
        val objectOutputStream = ObjectOutputStream(fileOutputStream)
        Log.e("serializeObjectToCache", "Try to serialize object to cache $fileName")
        objectOutputStream.writeObject(music)
        objectOutputStream.close()
        fileOutputStream.close()
    }
}

