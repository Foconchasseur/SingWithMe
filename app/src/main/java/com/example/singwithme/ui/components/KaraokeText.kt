package com.example.singwithme.ui.components

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

import com.example.singwithme.viewmodel.KaraokeViewModel
import kotlinx.coroutines.delay

@Composable
fun KaraokeText(modifier: Modifier, /*karaokeViewModel: KaraokeViewModel = viewModel()*/) {
    /*
    val currentLyric by karaokeViewModel.currentLyricLine.collectAsState()
    val progress by karaokeViewModel.progress.collectAsState()
    */
    //KaraokeSimpleText("currentLyric",0.5f, modifier = modifier)
}

