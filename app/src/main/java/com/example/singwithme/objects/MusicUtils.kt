package com.example.singwithme.objects

import android.content.Context
import com.example.singwithme.data.models.SongData
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream

object MusicUtils {
    fun loadMusicFromCache(context: Context, fileName: String): SongData {
        val cacheDir = context.cacheDir
        val file = File(cacheDir, fileName)
        return ObjectInputStream(FileInputStream(file)).use {
            it.readObject() as SongData
        }
    }
}
