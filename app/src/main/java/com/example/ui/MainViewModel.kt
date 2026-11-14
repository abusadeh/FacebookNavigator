package com.example.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppPreferences
import com.example.util.CustomTabHelper
import com.example.util.NetworkMonitor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val preferences = AppPreferences(application)
    val networkMonitor = NetworkMonitor(application)

    val isOnline: StateFlow<Boolean> = networkMonitor.isOnline

    private val _useCustomTabs = MutableStateFlow(preferences.useCustomTabs)
    val useCustomTabs: StateFlow<Boolean> = _useCustomTabs.asStateFlow()

    private val _autoOpenOnLaunch = MutableStateFlow(preferences.autoOpenOnLaunch)
    val autoOpenOnLaunch: StateFlow<Boolean> = _autoOpenOnLaunch.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _showSettingsDialog = MutableStateFlow(false)
    val showSettingsDialog: StateFlow<Boolean> = _showSettingsDialog.asStateFlow()

    private val _showAboutDialog = MutableStateFlow(false)
    val showAboutDialog: StateFlow<Boolean> = _showAboutDialog.asStateFlow()

    private val _snackBarMessage = MutableSharedFlow<String>()
    val snackBarMessage: SharedFlow<String> = _snackBarMessage.asSharedFlow()

    var pendingUrl: String = CustomTabHelper.FACEBOOK_HOME_URL
        private set

    init {
        if (preferences.autoOpenOnLaunch) {
            viewModelScope.launch {
                delay(1200L)
                if (networkMonitor.isNetworkAvailable()) {
                    CustomTabHelper.openUrl(getApplication(), CustomTabHelper.FACEBOOK_HOME_URL, preferences.useCustomTabs)
                } else {
                    _errorMessage.value = "Facebook could not be opened. Please check your internet connection and try again."
                }
            }
        }
    }

    fun openFacebook(context: Context, url: String = CustomTabHelper.FACEBOOK_HOME_URL, forceBrowser: Boolean = false) {
        pendingUrl = url
        val useTab = if (forceBrowser) false else _useCustomTabs.value
        val result = CustomTabHelper.openUrl(context, url, useTab)
        result.onFailure {
            _errorMessage.value = "Facebook could not be opened. Please check your internet connection and try again."
        }
    }

    fun openInBrowser(context: Context, url: String = CustomTabHelper.FACEBOOK_HOME_URL) {
        openFacebook(context, url, forceBrowser = true)
    }

    fun retry(context: Context) {
        _errorMessage.value = null
        openFacebook(context, pendingUrl)
    }

    fun shareFacebook(context: Context, url: String = CustomTabHelper.FACEBOOK_HOME_URL) {
        CustomTabHelper.shareUrl(context, url)
    }

    fun setUseCustomTabs(enabled: Boolean) {
        preferences.useCustomTabs = enabled
        _useCustomTabs.value = enabled
    }

    fun setAutoOpenOnLaunch(enabled: Boolean) {
        preferences.autoOpenOnLaunch = enabled
        _autoOpenOnLaunch.value = enabled
    }

    fun clearPreferences() {
        preferences.clear()
        _useCustomTabs.value = true
        _autoOpenOnLaunch.value = false
        viewModelScope.launch {
            _snackBarMessage.emit("Settings reset to defaults")
        }
    }

    fun dismissError() {
        _errorMessage.value = null
    }

    fun openSettings() {
        _showSettingsDialog.value = true
    }

    fun closeSettings() {
        _showSettingsDialog.value = false
    }

    fun openAbout() {
        _showAboutDialog.value = true
    }

    fun closeAbout() {
        _showAboutDialog.value = false
    }

    override fun onCleared() {
        super.onCleared()
        networkMonitor.unregister()
    }
}
