package com.tdtd.presentation.util

import android.media.MediaRecorder

object MediaRecorderHelper {
    private var mediaRecorder: MediaRecorder? = null
    var fileName: String = ""

    fun startRecording() {
        mediaRecorder = MediaRecorder()
        mediaRecorder?.let { mediaRecorder ->
            mediaRecorder.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setAudioEncodingBitRate(128000)
                setAudioSamplingRate(96000)
                setOutputFile(fileName)
            }
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