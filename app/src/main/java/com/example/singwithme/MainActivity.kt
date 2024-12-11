package com.example.singwithme

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.singwithme.data.models.Music
import com.example.singwithme.ui.components.KaraokeSimpleText
import com.example.singwithme.ui.components.MusicCard
import com.example.singwithme.ui.components.MusicGridCard
import com.example.singwithme.ui.screens.PlaybackScreen
import com.example.singwithme.back.cache.WorkerCachePlaylist
import com.example.singwithme.ui.theme.SingWithMeTheme
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkInfo.State
import com.example.singwithme.back.Lyrics.Music
import com.example.singwithme.back.Lyrics.WorkerMd2Lyrics
import com.example.singwithme.back.cache.Playlist
import com.example.singwithme.back.cache.Song
import com.example.singwithme.back.cache.WorkerCacheSong
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileReader


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        println("Hello")

        val savePlaylistRequest = OneTimeWorkRequestBuilder<WorkerCachePlaylist>().build()
        val saveMusicRequest = OneTimeWorkRequestBuilder<WorkerCacheSong>().build()
        val md2LyricsRequest = OneTimeWorkRequestBuilder<WorkerMd2Lyrics>().build()

        WorkManager.getInstance(this)
            .beginWith(savePlaylistRequest)
            .then(saveMusicRequest)
            .then(md2LyricsRequest)
            .enqueue()

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(savePlaylistRequest.id).observe(this) { workInfo ->
            if (workInfo != null && workInfo.state == State.SUCCEEDED) {
                val playlist = Playlist()
                playlist.songs = readJsonFromCache(this)
                println(playlist)
            }
        }

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(md2LyricsRequest.id).observe(this) { workInfo ->
            if (workInfo != null && workInfo.state == State.SUCCEEDED) {
                val music = Music.deserializeObjectFromCache(this, "Bohemian Rapsodie.ser")
                Log.e("Music", music.toString())
            }
        }

        setContent {
            PlaybackScreen(
                lyricsPath = "Here are the lyrics of the song scrolling across the screen...",
                onPauseClick = {},
                onRestartClick = {},
                onMenuClick = {},

            )
        }
    }

    fun readJsonFromCache(context: Context): List<Song>? {
        val cacheDir = context.cacheDir
        val jsonFile = File(cacheDir, "playlist.json")
        if (jsonFile.exists()) {
            FileReader(jsonFile).use { reader ->
                val gson = Gson()
                val songListType = object : TypeToken<List<Song>>() {}.type
                return gson.fromJson(reader, songListType)
            }
        }
        return null
    }
}

@Composable
fun KaraokeSimpleTextAnimate(duration: Int, text: String) {
    val karaokeAnimation = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        karaokeAnimation.animateTo(1f, tween(duration, easing = LinearEasing))
    }
    KaraokeSimpleText(text, text, text,  karaokeAnimation.value, modifier = Modifier)
}

@Preview(showBackground = true)
@Composable
fun KaraokeSimpleTextAnimatePreview(){
    KaraokeSimpleTextAnimate(5, "ceci est un test")
}







