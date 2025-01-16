import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.singwithme.KaraokeApplication
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import java.io.File
import kotlin.math.exp
import kotlin.math.truncate

class KaraokeViewModel() : ViewModel() {


    ///
    // Gestion du player MP3
    ///
    // ExoPlayer instance
    @SuppressLint("StaticFieldLeak")
    private val context = KaraokeApplication.instance.applicationContext
    var exoPlayer : ExoPlayer? = null
    // LiveData to observe the playback state
    private var isPlaying = true;

    private var duration = 0L;

    fun getIsPlaying(): Boolean{
        return isPlaying
    }
    fun initializePlayer(context: Context, uri: Uri) {
        exoPlayer?.release() // Libérer l'ancienne instance si elle existe
        exoPlayer = ExoPlayer.Builder(context).build()

        exoPlayer?.setMediaItem(MediaItem.fromUri(uri))

        exoPlayer?.seekTo(0L)
        exoPlayer?.prepare()

        if (isPlaying) {
            exoPlayer?.play() // Reprendre la lecture si elle était en cours
        }


    }
    fun release() {
        exoPlayer?.release()
    }

    // Pause the audio
    fun pause() {
        if (isPlaying) {
            exoPlayer?.pause()
            isPlaying = false
        } else {
            exoPlayer?.play()
            isPlaying = true
        }

    }

    // Stop the audio
    fun stop() {
        exoPlayer?.release()
        isPlaying = false
    }

    fun getCurrentPosition(): Long? {
        return exoPlayer?.currentPosition
    }

    fun setCurrentPosition(position: Long) {
        exoPlayer?.seekTo(position)
    }

    fun getMusicLength(): Long {
        duration = exoPlayer?.duration ?: 0
        return if (duration<=0){
            0
        } else {
            return duration.div(1000)
        }
    }

    fun reset() {
        exoPlayer?.seekTo(0L)
        if (!isPlaying) {
            exoPlayer?.play()
        }
    }

    fun setPlaying(isplaying : Boolean){
        isPlaying = isplaying
    }
    // Release the player when the ViewModel is cleared
    override fun onCleared() {
        super.onCleared()
        exoPlayer?.release()
    }
}
