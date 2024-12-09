package com.example.singwithme.back.cache

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.singwithme.Constants
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.FileWriter

class WorkerCacheSong(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        println("Hello from Caching Song")
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Constants.PLAYLIST_URL+"/Bohemian/Bohemian.md")
            .build()

        val response: Response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val responseData = response.body?.string()
            Log.e("responseData", responseData.toString())

            // Save Md response to cache directory
            responseData?.let {
                saveMdToCache(it, "Bohemian")
            }

            return Result.Success()

        } else {
            println("Request failed with code: ${response.code}")
        }
        return Result.Success()
    }

    private fun saveMdToCache(jsonData: String, name: String) {
        val cacheDir = applicationContext.cacheDir
        val MdFile = File(cacheDir, "$name.md")
        Log.e("MdFile", "Try to save Md file to cache")
        FileWriter(MdFile).use { writer ->
            writer.write(jsonData)
        }
    }
}