package com.smartplant.smartplantandroid.core.ui

import android.content.Context
import com.smartplant.smartplantandroid.R

object ThemeManager {
    private const val PREFS_NAME = "theme_prefs"
    private const val KEY_THEME = "current_theme"

    enum class Theme(val value: String) {
        LIGHT("Theme.SmartPlantAndroid.Light"),
        DARK("Theme.SmartPlantAndroid.Dark"),
    }

    fun applyTheme(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val themeName = prefs.getString(KEY_THEME, Theme.LIGHT.value) ?: Theme.LIGHT.value

        when (themeName) {
            Theme.LIGHT.value -> context.setTheme(R.style.Theme_SmartPlantAndroid_Light)
            Theme.DARK.value -> context.setTheme(R.style.Theme_SmartPlantAndroid_Dark)
        }
    }

    fun setTheme(context: Context, theme: Theme) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_THEME, theme.value).apply()
        applyTheme(context)
    }
}