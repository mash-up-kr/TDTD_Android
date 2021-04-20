package com.tdtd.voicepaper.di.module

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.tdtd.data.api.AdminApi
import com.tdtd.data.api.BookmarkApi
import com.tdtd.data.api.ReplyApi
import com.tdtd.data.api.RoomApi
import com.tdtd.presentation.util.Constants.BASE_URL
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
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            //.addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create()
                )
            )
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClientForAccessToken(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        // header
        builder.addInterceptor { chain ->
            val deviceId = "device-11"
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

    @Provides
    @Singleton
    fun provideRoomApiService(retrofit: Retrofit) = retrofit.create(RoomApi::class.java)

    @Provides
    @Singleton
    fun provideReplyApiService(retrofit: Retrofit) = retrofit.create(ReplyApi::class.java)

    @Provides
    @Singleton
    fun provideBookmarkApiService(retrofit: Retrofit) = retrofit.create(BookmarkApi::class.java)

    @Provides
    @Singleton
    fun provideAdminApiService(retrofit: Retrofit) = retrofit.create(AdminApi::class.java)

}
