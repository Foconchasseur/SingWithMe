package com.example.singwithme.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

/**
 * ViewModel pour gérer la lecture audio avec ExoPlayer dans l'application de karaoké.
 *
 * Cette classe fournit des fonctionnalités pour initialiser, contrôler et gérer
 * la lecture d'un fichier audio. Elle garantit également une libération propre des ressources
 * associées à ExoPlayer lorsque le ViewModel est détruit.
 */
open class KaraokeViewModel : ViewModel() {

    /** Instance d'ExoPlayer utilisée pour la lecture audio. */
    var exoPlayer: ExoPlayer? = null

    /** Indique si la lecture est en cours. */
    private var isPlaying = true

    /** Durée totale de la piste audio en millisecondes. */
    private var duration = 0L

    /**
     * Renvoie l'état actuel de la lecture.
     *
     * @return `true` si la lecture est en cours, `false` sinon.
     */
    open fun getIsPlaying(): Boolean {
        return isPlaying
    }

    /**
     * Initialise le lecteur ExoPlayer avec un fichier audio spécifié par son URI.
     *
     * @param context Le contexte utilisé pour créer ExoPlayer.
     * @param uri L'URI du fichier audio à lire.
     */
    fun initializePlayer(context: Context, uri: Uri) {
        // Libérer et réinitialiser l'ancienne instance
        exoPlayer?.release()
        exoPlayer = null

        // Créer une nouvelle instance d'ExoPlayer
        exoPlayer = ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(uri))
            prepare() // Préparer le lecteur
            if (isPlaying) play() // Reprendre la lecture si elle était active
        }
    }

    /**
     * Libère les ressources associées à ExoPlayer.
     */
    fun release() {
        exoPlayer?.release()
    }

    /**
     * Met la lecture en pause ou la reprend si elle est déjà en pause.
     */
    open fun pause() {
        if (isPlaying) {
            exoPlayer?.pause()
            isPlaying = false
        } else {
            exoPlayer?.play()
            isPlaying = true
        }
    }

    /**
     * Arrête la lecture et libère ExoPlayer.
     */
    fun stop() {
        exoPlayer?.release()
        isPlaying = false
    }

    /**
     * Renvoie la position actuelle de lecture en millisecondes.
     *
     * @return La position actuelle ou `null` si ExoPlayer n'est pas initialisé.
     */
    open fun getCurrentPosition(): Long? {
        return exoPlayer?.currentPosition
    }

    /**
     * Définit la position actuelle de lecture.
     *
     * @param position La position en millisecondes à laquelle déplacer la lecture.
     */
    open fun setCurrentPosition(position: Long) {
        exoPlayer?.seekTo(position)
    }

    /**
     * Renvoie la durée totale de la piste audio en secondes.
     *
     * @return La durée de la piste ou `0` si la durée n'est pas disponible.
     */
    fun getMusicLength(): Long {
        duration = exoPlayer?.duration ?: 0
        return if (duration <= 0) {
            0
        } else {
            duration / 1000
        }
    }

    /**
     * Réinitialise la lecture à la position de début (0 ms) et reprend la lecture si elle était en pause.
     */
    fun reset() {
        exoPlayer?.seekTo(0L)
        if (!isPlaying) {
            exoPlayer?.play()
        }
    }

    /**
     * Définit l'état de lecture (en cours ou en pause).
     *
     * @param isPlaying `true` pour indiquer que la lecture est en cours, `false` sinon.
     */
    fun setPlaying(isPlaying: Boolean) {
        this.isPlaying = isPlaying
    }

    /**
     * Libère les ressources associées à ExoPlayer lorsque le ViewModel est détruit.
     */
    override fun onCleared() {
        super.onCleared()
        exoPlayer?.release()
    }
}
