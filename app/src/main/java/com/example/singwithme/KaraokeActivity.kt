package com.example.singwithme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.singwithme.R
import com.example.singwithme.data.models.LyricsLine
import com.example.singwithme.data.models.Music
import com.example.singwithme.ui.screens.PlaybackScreen
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class KaraokeActivity : ComponentActivity() {

    private lateinit var exoPlayer: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val music: Music? = intent.getSerializableExtra("MUSIC_EXTRA") as? Music

        // Initialisation d'ExoPlayer
        exoPlayer = ExoPlayer.Builder(this).build()
        val mediaItem = MediaItem.fromUri("android.resource://$packageName/${R.raw.queen}")
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()

        setContent {
            if (music != null) {
                PlaybackScreen(
                    lyrics = music.lyrics,
                    exoPlayer = exoPlayer,
                    onMenuClick = { finish() },
                    onPauseClick = {
                        if (exoPlayer.isPlaying) {
                            exoPlayer.pause() // Met en pause
                        } else {
                            exoPlayer.play()  // Si déjà en pause, relance la lecture
                        }
                    }
                    ,
                    onRestartClick = {
                        exoPlayer.seekTo(0)
                        exoPlayer.play()
                    }
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
    }
}
