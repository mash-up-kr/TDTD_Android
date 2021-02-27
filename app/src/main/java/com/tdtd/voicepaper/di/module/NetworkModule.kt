package com.tdtd.voicepaper.di.module

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.tdtd.presentation.util.Constants
import com.tdtd.voicepaper.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideUserRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val baseUrl: String = Constants.BASE_URL
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClientForAccessToken(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        // header
        builder.addInterceptor { chain ->
                val deviceId = "test-device"
                var request = chain.request()
                request = request.newBuilder()
                    .addHeader("Device-Id", deviceId)
                    .build()

                chain.proceed(request)
            }

        // log
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }

        // timeout
        builder.readTimeout(1, TimeUnit.MINUTES)
        builder.connectTimeout(30, TimeUnit.SECONDS)

        return builder.build()
    }
}
