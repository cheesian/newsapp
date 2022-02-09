package com.programiqsolutions.news.utils

import android.app.Activity
import android.view.Window
import android.view.WindowManager

/**
Created by ian
 */

object FullScreen {
//    Should be called before the contentView is set
    fun setFullScreen (activity: Activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE)
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}