package com.example.singwithme.back.cache

import androidx.work.CoroutineWorker
import android.content.Context
import androidx.work.WorkerParameters
import com.example.singwithme.Constants
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.FileWriter


class WorkerCachePlaylist(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        println("Hello from Caching")
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Constants.PLAYLIST_URL+"/playlist.json") //Récupère le fichier de playlist
            .build()

        val response: Response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val responseData = response.body?.string()
            println(responseData)

            // Save JSON response to cache directory
            responseData?.let {
                saveJsonToCache(it)
            }

            return Result.Success()

        } else {
            println("Request failed with code: ${response.code}")
        }


        return Result.Failure()
    }

    private fun saveJsonToCache(jsonData: String) {
        val cacheDir = applicationContext.cacheDir
        val jsonFile = File(cacheDir, "playlist.json")
        FileWriter(jsonFile).use { writer ->
            writer.write(jsonData)
        }
    }
}