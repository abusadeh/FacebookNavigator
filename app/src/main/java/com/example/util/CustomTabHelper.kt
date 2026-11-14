package com.example.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent

object CustomTabHelper {
    const val FACEBOOK_HOME_URL = "https://www.facebook.com/"
    const val FACEBOOK_MESSAGES_URL = "https://www.facebook.com/messages/"
    const val FACEBOOK_NOTIFICATIONS_URL = "https://www.facebook.com/notifications/"
    const val FACEBOOK_MARKETPLACE_URL = "https://www.facebook.com/marketplace/"
    const val FACEBOOK_WATCH_URL = "https://www.facebook.com/watch/"
    const val FACEBOOK_GROUPS_URL = "https://www.facebook.com/groups/"

    private val TOOLBAR_COLOR = Color.parseColor("#1877F2")
    private val NAVIGATION_BAR_COLOR = Color.parseColor("#0A2540")

    fun openUrl(context: Context, url: String = FACEBOOK_HOME_URL, useCustomTab: Boolean = true): Result<Unit> {
        return try {
            val uri = Uri.parse(url)
            if (useCustomTab) {
                launchCustomTab(context, uri)
            } else {
                launchExternalBrowser(context, uri)
            }
            Result.success(Unit)
        } catch (e: ActivityNotFoundException) {
            try {
                launchExternalBrowser(context, Uri.parse(url))
                Result.success(Unit)
            } catch (fallbackEx: Exception) {
                Result.failure(fallbackEx)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun launchCustomTab(context: Context, uri: Uri) {
        val colorParams = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(TOOLBAR_COLOR)
            .setNavigationBarColor(NAVIGATION_BAR_COLOR)
            .build()

        val intentBuilder = CustomTabsIntent.Builder()
            .setDefaultColorSchemeParams(colorParams)
            .setShowTitle(true)
            .setUrlBarHidingEnabled(true)
            .setShareState(CustomTabsIntent.SHARE_STATE_ON)
            .setStartAnimations(context, android.R.anim.fade_in, android.R.anim.fade_out)
            .setExitAnimations(context, android.R.anim.fade_in, android.R.anim.fade_out)

        val customTabsIntent = intentBuilder.build()
        if (context !is Activity) {
            customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        customTabsIntent.launchUrl(context, uri)
    }

    fun launchExternalBrowser(context: Context, uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            if (context !is Activity) {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        }
        context.startActivity(intent)
    }

    fun shareUrl(context: Context, url: String = FACEBOOK_HOME_URL) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
            if (context !is Activity) {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        }
        context.startActivity(Intent.createChooser(intent, "Share Facebook"))
    }
}
