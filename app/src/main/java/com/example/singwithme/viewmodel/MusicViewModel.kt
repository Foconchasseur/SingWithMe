package com.example.singwithme.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.singwithme.back.cache.Song
import com.example.singwithme.worker.TaskManager
import kotlinx.coroutines.launch
import java.io.File

class MusicViewModel (private val context: Context) : ViewModel(){
    private val taskManager = TaskManager(context)

    fun downloadAndSerializeSong(id: String) {
        viewModelScope.launch {
            taskManager.downloadAndSerializeMusic(id)
        }
    }
}