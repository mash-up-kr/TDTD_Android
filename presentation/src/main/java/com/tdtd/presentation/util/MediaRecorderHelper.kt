package com.tdtd.presentation.util

import android.media.MediaRecorder

object MediaRecorderHelper {
    private var mediaRecorder: MediaRecorder? = null
    var fileName: String = ""

    fun startRecording() {
        mediaRecorder = MediaRecorder()
        mediaRecorder?.let { mediaRecorder ->
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB)
            mediaRecorder.setOutputFile(fileName)
            try {
                mediaRecorder.prepare()
                mediaRecorder.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun stopAndRelease() {
        mediaRecorder?.let { recorder ->
            recorder.stop()
            recorder.release()
        }
        mediaRecorder = null
    }
}