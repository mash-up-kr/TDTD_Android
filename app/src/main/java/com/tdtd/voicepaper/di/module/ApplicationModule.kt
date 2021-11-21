package com.tdtd.voicepaper.di.module

import android.content.Context
import com.tdtd.presentation.utils.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun providePrefManager(@ApplicationContext context: Context): PreferenceManager =
        PreferenceManager(context)
}