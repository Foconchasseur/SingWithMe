package com.example.singwithme.data.models

import android.content.Context
import java.io.File
import java.io.Serializable
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.io.FileInputStream
import java.io.ObjectInputStream

data class SongData(val title: String, val artist: String, val lyrics: List<LyricsLine>, val trackPath: String) : Serializable {

    companion object {
        fun serializeObjectToCache(context: Context, songData: SongData, fileName: String) {
            val cacheDir = context.cacheDir
            val file = File(cacheDir, fileName)
            val fileOutputStream = FileOutputStream(file)
            val objectOutputStream = ObjectOutputStream(fileOutputStream)
            objectOutputStream.writeObject(songData)
            objectOutputStream.close()
            fileOutputStream.close()
        }

        fun deserializeObjectFromCache(context: Context, fileName: String): SongData {
            val cacheDir = context.cacheDir
            val file = File(cacheDir, fileName)
            val fileInputStream = FileInputStream(file)
            val objectInputStream = ObjectInputStream(fileInputStream)
            val songData = objectInputStream.readObject() as SongData
            objectInputStream.close()
            fileInputStream.close()
            return songData
        }

        override fun toString(): String {
            return super.toString()
        }
    }
}