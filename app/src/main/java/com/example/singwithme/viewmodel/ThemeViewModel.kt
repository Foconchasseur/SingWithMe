package com.example.singwithme.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel pour gérer le thème de l'application.
 *
 * Cette classe permet de sauvegarder, charger et changer le thème en utilisant
 * [SharedPreferences] pour la persistance et un [StateFlow] pour l'état réactif.
 *
 * @param context Le contexte de l'application, utilisé pour accéder à [SharedPreferences].
 */
class ThemeViewModel(private val context: Context) : ViewModel() {

    /**
     * État réactif représentant l'index du thème actuellement sélectionné.
     *
     * L'index correspond à une valeur entière qui identifie un thème spécifique.
     */
    private val _currentThemeIndex = MutableStateFlow(loadThemePreference())

    /**
     * Flux exposé publiquement représentant l'index du thème actuel.
     */
    val currentThemeIndex: StateFlow<Int> = _currentThemeIndex

    /**
     * Change le thème actuel et sauvegarde la préférence.
     *
     * @param themeIndex L'index du thème à appliquer.
     */
    fun setTheme(themeIndex: Int) {
        _currentThemeIndex.value = themeIndex
        saveThemePreference(themeIndex)
    }

    /**
     * Sauvegarde l'index du thème sélectionné dans [SharedPreferences].
     *
     * @param themeIndex L'index du thème à sauvegarder.
     */
    private fun saveThemePreference(themeIndex: Int) {
        val sharedPreferences = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("selected_theme", themeIndex)
        editor.apply()
    }

    /**
     * Charge l'index du thème depuis [SharedPreferences].
     *
     * @return L'index du thème précédemment sélectionné, ou `0` si aucune préférence n'est enregistrée.
     */
    private fun loadThemePreference(): Int {
        val sharedPreferences = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("selected_theme", 0) // Valeur par défaut : LightTheme (index 0)
    }
}
