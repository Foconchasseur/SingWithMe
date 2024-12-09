package com.example.singwithme.back.Lyrics

import android.content.Context
import java.io.File
import java.io.Serializable
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.io.FileInputStream
import java.io.ObjectInputStream

data class Music(val title: String, val artist: String, val lyrics: List<LyricsLine>, val trackPath: String) : Serializable {

    companion object {
        fun serializeObjectToCache(context: Context, music: Music, fileName: String) {
            val cacheDir = context.cacheDir
            val file = File(cacheDir, fileName)
            val fileOutputStream = FileOutputStream(file)
            val objectOutputStream = ObjectOutputStream(fileOutputStream)
            objectOutputStream.writeObject(music)
            objectOutputStream.close()
            fileOutputStream.close()
        }

        fun deserializeObjectFromCache(context: Context, fileName: String): Music {
            val cacheDir = context.cacheDir
            val file = File(cacheDir, fileName)
            val fileInputStream = FileInputStream(file)
            val objectInputStream = ObjectInputStream(fileInputStream)
            val music = objectInputStream.readObject() as Music
            objectInputStream.close()
            fileInputStream.close()
            return music
        }

        override fun toString(): String {
            return super.toString()
        }
    }
}