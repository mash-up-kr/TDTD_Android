package com.tdtd.voicepaper

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.tdtd.presentation.utils.DeviceInfo
import com.tdtd.presentation.utils.PreferenceManager
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class TDTDApplication : Application() {

    companion object {
        lateinit var preferences: PreferenceManager
    }

    override fun onCreate() {
        super.onCreate()

        MobileAds.initialize(this)
    }
}