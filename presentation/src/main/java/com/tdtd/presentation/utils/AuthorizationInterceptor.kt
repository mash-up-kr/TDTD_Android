package com.tdtd.presentation.utils

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val prefManager: PreferenceManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .apply {
                    prefManager.getToken()?.let {
                        header("Device-Id", it)
                    }
                }
                .build()
        )
    }
}