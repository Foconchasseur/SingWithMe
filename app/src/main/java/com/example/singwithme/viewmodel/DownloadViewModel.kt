package com.example.singwithme.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.singwithme.objects.Playlist
import com.example.singwithme.worker.TaskManager
import kotlinx.coroutines.launch
import java.io.File

class DownloadViewModel (private val context: Context) : ViewModel(){
    private val taskManager = TaskManager(context)

    fun downloadAndSerializeSong(id: String) {
        viewModelScope.launch {
            taskManager.downloadAndSerializeMusic(id)
        }
    }

    fun deleteDownloadedFiles(context: Context, fileName: String, id : String) {
        val file1 = File(context.cacheDir, "$fileName.ser")
        val file2 = File(context.cacheDir, "$fileName.mp3")
        println("Chemin du fichier 1: ${file1.absolutePath}")
        println("Chemin du fichier 2: ${file2.absolutePath}")
        if (file1.exists() && file2.exists()) {
            val deleted1 = file1.delete()
            val deleted2 = file2.delete()
            if (deleted1 && deleted2) {
                Playlist.updateDownloadedById(id,false,true)
                println("Les fichiers ont été supprimé du cache.")
            } else {
                println("Erreur lors de la suppression des fichier.")
            }
        } else {
            println("Les fichiers n'existent pas dans le cache.")
        }
    }

}