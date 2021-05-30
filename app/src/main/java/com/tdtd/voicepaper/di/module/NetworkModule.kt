package com.tdtd.voicepaper.di.module

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.tdtd.data.api.AdminApi
import com.tdtd.data.api.BookmarkApi
import com.tdtd.data.api.ReplyApi
import com.tdtd.data.api.RoomApi
import com.tdtd.presentation.util.AuthorizationInterceptor
import com.tdtd.presentation.util.Constants.BASE_URL
import com.tdtd.presentation.util.PreferenceManager
import com.tdtd.voicepaper.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideUserRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
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
    fun provideOkHttpClientForAccessToken(
        @ApplicationContext context: Context,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    val loggingInterceptor = HttpLoggingInterceptor()
                    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    addInterceptor(loggingInterceptor)
                }
            }
            .addInterceptor(AuthorizationInterceptor(PreferenceManager(context)))
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
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
