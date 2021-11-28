package com.tdtd.voicepaper.di.module

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.tdtd.data.api.AdminApi
import com.tdtd.data.api.BookmarkApi
import com.tdtd.data.api.ReplyApi
import com.tdtd.data.api.RoomApi
import com.tdtd.presentation.utils.AuthorizationInterceptor
import com.tdtd.presentation.utils.Constants.BASE_URL
import com.tdtd.presentation.utils.PreferenceManager
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

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
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
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideRoomApiService(retrofit: Retrofit): RoomApi = retrofit.create(RoomApi::class.java)

    @Provides
    fun provideReplyApiService(retrofit: Retrofit): ReplyApi = retrofit.create(ReplyApi::class.java)

    @Provides
    fun provideBookmarkApiService(retrofit: Retrofit): BookmarkApi =
        retrofit.create(BookmarkApi::class.java)

    @Provides
    fun provideAdminApiService(retrofit: Retrofit): AdminApi = retrofit.create(AdminApi::class.java)
}
