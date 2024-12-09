package com.example.singwithme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class KaraokeViewModel : ViewModel() {
    private val _lyrics = MutableStateFlow("Initializing lyrics...")
    val lyrics: StateFlow<String> = _lyrics

    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _currentTime = MutableStateFlow(0L) // Temps écoulé en millisecondes

    val currentTime: StateFlow<Long> = _currentTime

    private val _currentLyricLine = MutableStateFlow("")
    val currentLyricLine: StateFlow<String> = _currentLyricLine

    fun loadLyrics(newLyrics: String) {
        _lyrics.value = newLyrics
    }

    fun resetTimer() {
        _currentTime.value = 0L
        _isPlaying.value = false
    }

    fun togglePlayPause() {
        _isPlaying.value = !_isPlaying.value
        if (_isPlaying.value) {
            startTimer() // Reprendre le timer
        }
    }

    fun startTimer() {
        viewModelScope.launch {
            while (_isPlaying.value) { // Tant que la lecture est active
                delay(100) // Attendre 100 ms
                _currentTime.value += 100 // Incrémenter de 100 ms
            }
        }
    }
}
