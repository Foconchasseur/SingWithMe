package com.example.singwithme.viewmodel

import android.content.Context
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ThemeViewModel(private val context: Context) : ViewModel() {

    // Utilisation de StateFlow pour l'état du thème
    private val _currentThemeIndex = MutableStateFlow(loadThemePreference())
    val currentThemeIndex: StateFlow<Int> = _currentThemeIndex

    // Fonction pour changer de thème
    fun setTheme(themeIndex: Int) {
        _currentThemeIndex.value = themeIndex
        saveThemePreference(themeIndex)
    }

    // Sauvegarder le thème dans SharedPreferences
    private fun saveThemePreference(themeIndex: Int) {
        val sharedPreferences = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("selected_theme", themeIndex)
        editor.apply()
    }

    // Charger le thème depuis SharedPreferences
    private fun loadThemePreference(): Int {
        val sharedPreferences = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("selected_theme", 0) // Valeur par défaut : LightTheme (index 0)
    }
}