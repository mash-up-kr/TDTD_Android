package com.tdtd.presentation.util

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings

class DeviceInfo(val context: Context) {
    @SuppressLint("HardwareIds")
    fun getDeviceId(): String =
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}