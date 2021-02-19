package com.tdtd.voicepaper

import android.app.Application
import androidx.databinding.library.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class TDTDApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        //Initialize Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}