package com.tdtd.presentation.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferenceManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, 0)

    fun getToken(): String? = prefs.getString(AUTH_TOKEN, null)

    fun saveToken(token: String) = prefs.edit { putString(AUTH_TOKEN, token) }

    companion object {
        const val AUTH_TOKEN = "auth_token"
        const val PREFS_NAME = "prefs"
    }
}

