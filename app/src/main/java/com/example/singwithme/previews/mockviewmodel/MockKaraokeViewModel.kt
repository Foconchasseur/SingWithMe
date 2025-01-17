class MockKaraokeViewModel : KaraokeViewModel() {

    // Mock of the ExoPlayer
    private var exoPlayerMock: MockExoPlayer? = MockExoPlayer()
    private var isPlaying = true

    override fun getIsPlaying(): Boolean {
        return isPlaying
    }

    override fun pause() {
        if (isPlaying) {
            exoPlayerMock?.pause()
            isPlaying = false
        } else {
            exoPlayerMock?.play()
            isPlaying = true
        }
    }

    override fun getCurrentPosition(): Long? {
        return exoPlayerMock?.currentPosition
    }

    override fun setCurrentPosition(position: Long) {
        exoPlayerMock?.seekTo(position)
    }


    // Mock ExoPlayer class
    class MockExoPlayer {

        var currentPosition: Long = 0L

        fun play() {
            // Mock behavior to simulate playing
        }

        fun pause() {
            // Mock behavior to simulate pausing
        }

        fun seekTo(position: Long) {
            currentPosition = position
        }
    }
}
