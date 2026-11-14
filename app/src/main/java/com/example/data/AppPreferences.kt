package com.example.data

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {
    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var useCustomTabs: Boolean
        get() = prefs.getBoolean(KEY_USE_CUSTOM_TABS, true)
        set(value) = prefs.edit().putBoolean(KEY_USE_CUSTOM_TABS, value).apply()

    var autoOpenOnLaunch: Boolean
        get() = prefs.getBoolean(KEY_AUTO_OPEN_ON_LAUNCH, false)
        set(value) = prefs.edit().putBoolean(KEY_AUTO_OPEN_ON_LAUNCH, value).apply()

    fun clear() {
        prefs.edit().clear().apply()
    }

    companion object {
        private const val PREFS_NAME = "facebook_navigator_prefs"
        private const val KEY_USE_CUSTOM_TABS = "key_use_custom_tabs"
        private const val KEY_AUTO_OPEN_ON_LAUNCH = "key_auto_open_on_launch"
    }
}
