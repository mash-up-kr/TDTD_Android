package com.tdtd.voicepaper.di.module

import android.content.Context
import com.tdtd.presentation.utils.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun providePrefManager(@ApplicationContext context: Context): PreferenceManager =
        PreferenceManager(context)
}