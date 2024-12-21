package com.example.singwithme.repository

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.await
import androidx.work.workDataOf
import com.example.singwithme.back.WorkerDownloadAndSerialize
import com.example.singwithme.back.cache.Song
import com.example.singwithme.data.models.Music
import com.example.singwithme.worker.TaskManager
import com.example.singwithme.worker.WorkerDownloadMp3
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class MusicRepository(private val context: Context) {

    private val taskManager = TaskManager(context)

    // Fonction pour télécharger et sérialiser la musique
    suspend fun downloadAndSerializeMusic(
        id: String,
    )
     {
        return taskManager.downloadAndSerializeMusic(id)
    }
}