package com.example.singwithme.viewmodel

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ErrorViewModel {
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun showError(message: String) {
        Log.e("ErrorViewModel", "Error: $message")
        _errorMessage.value = message
    }

    fun clearError() {
        _errorMessage.value = null
    }
}