package fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.viewmodel

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel utilisé pour gérer les erreurs et fournir une interface réactive pour afficher et effacer les messages d'erreur.
 *
 * Cette classe utilise un [StateFlow] pour surveiller les messages d'erreur et notifier les observateurs lorsque
 * de nouvelles erreurs sont enregistrées ou lorsqu'elles sont effacées.
 */
open class ErrorViewModel {
    private val _errorMessage = MutableStateFlow<String?>(null)

    /**
     * Flux représentant le message d'erreur actuel.
     *
     * Les abonnés à ce flux sont informés des mises à jour, qu'il s'agisse d'un nouveau message
     * ou d'une remise à zéro à `null`.
     */
    open val errorMessage: StateFlow<String?> = _errorMessage

    /**
     * Enregistre un message d'erreur et le rend disponible via le flux [errorMessage].
     *
     * @param message Le message d'erreur à afficher.
     */
    open fun showError(message: String) {
        Log.e("ErrorViewModel", "Error: $message")
        _errorMessage.value = message
    }

    /**
     * Efface le message d'erreur actuel.
     *
     * Cette méthode réinitialise la valeur de [errorMessage] à `null`, signalant que
     * plus aucune erreur n'est active.
     */
    open fun clearError() {
        _errorMessage.value = null
    }
}
