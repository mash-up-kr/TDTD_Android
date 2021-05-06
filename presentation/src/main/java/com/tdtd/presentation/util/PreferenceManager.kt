package com.tdtd.presentation.util

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, 0)

    fun setFavorite(isChecked: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(FAVORITE, isChecked)
        editor.apply()
    }

    fun getFavorite(): Boolean = prefs.getBoolean(FAVORITE, false)

    companion object {
        const val PREFS_NAME = "prefs"
        const val FAVORITE = "favorite"
    }
}

