package com.tdtd.presentation.util

import android.media.MediaPlayer

object MediaPlayerHelper {

    private var mediaPlayer: MediaPlayer? = null
    var position = 0

    fun startPlaying(path: String, onCompletionListener: MediaPlayer.OnCompletionListener) {
        mediaPlayer = MediaPlayer()

        mediaPlayer?.let { mediaPlayer ->
            mediaPlayer.setDataSource(path)
            mediaPlayer.prepare()

            if (mediaPlayer.isPlaying) {
                mediaPlayer.start()
            } else {
                mediaPlayer.seekTo(position)
                mediaPlayer.start()
            }

            onCompletionListener.let {
                if (position != 0) mediaPlayer.setOnCompletionListener(it).apply { position = 0 }
                else mediaPlayer.setOnCompletionListener(it)
            }

            mediaPlayer.setOnErrorListener { _, _, _ ->
                mediaPlayer.release()
                return@setOnErrorListener false
            }
        }
    }

    fun pausePlaying() {
        mediaPlayer?.let { player ->
            player.isPlaying.let {
                player.pause()
                position = player.currentPosition
            }
        }
    }

    fun stopAndRelease() {
        mediaPlayer?.let { player ->
            player.stop()
            player.release()
            mediaPlayer = null
        }
    }
}