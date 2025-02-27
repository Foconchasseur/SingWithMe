package fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.objects

import android.content.Context
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.SongData
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream

/**
 * Fournit des utilitaires pour gérer la musique dans le cache.
 *
 * Cet objet contient des méthodes pour charger les données de musique depuis le cache
 * de l'application en utilisant la sérialisation.
 */
object MusicUtils {

    /**
     * Charge une chanson depuis le cache de l'application.
     *
     * @param context Le contexte de l'application, utilisé pour accéder au cache.
     * @param fileName Le nom du fichier contenant les données de la chanson à charger.
     * @return Les données de la chanson sous forme d'objet [SongData].
     */
    fun loadMusicFromCache(context: Context, fileName: String): fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.SongData {
        val cacheDir = context.cacheDir
        val file = File(cacheDir, fileName)
        return ObjectInputStream(FileInputStream(file)).use {
            it.readObject() as fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.SongData
        }
    }
}
