package com.example.singwithme

import android.app.Application

/**
 * Application principale de l'application "SingWithMe" qui gère l'initialisation de l'application
 * et fournit une instance globale de l'application via la propriété [instance].
 *
 * Cette classe est utilisée pour gérer l'application à un niveau global et pour faciliter l'accès
 * à cette instance dans d'autres parties du code, si nécessaire.
 */
class KaraokeApplication : Application() {
    companion object {
        /**
         * Instance statique de l'application.
         * Permet d'accéder à l'application depuis n'importe quelle partie de l'application.
         */
        lateinit var instance: KaraokeApplication
            private set
    }

    /**
     * Méthode appelée lors de la création de l'application. Elle initialise l'instance globale
     * de l'application pour permettre un accès facile dans le reste du code.
     */
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
