package com.tdtd.presentation.utils

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

object MultiPartForm {

    fun getBody(key: String, value: Any): MultipartBody.Part {
        return MultipartBody.Part.createFormData(key, value.toString())
    }

    fun getAudioBody(key: String, file: File): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            name = key,
            filename = file.name,
            body = file.asRequestBody("audio/*".toMediaType())
        )
    }
}