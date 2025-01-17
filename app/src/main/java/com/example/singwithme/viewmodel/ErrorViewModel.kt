package com.example.singwithme.viewmodel

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class ErrorViewModel {
    private val _errorMessage = MutableStateFlow<String?>(null)
    open val errorMessage: StateFlow<String?> = _errorMessage

    open fun showError(message: String) {
        Log.e("ErrorViewModel", "Error: $message")
        _errorMessage.value = message
    }

    open fun clearError() {
        _errorMessage.value = null
    }
}