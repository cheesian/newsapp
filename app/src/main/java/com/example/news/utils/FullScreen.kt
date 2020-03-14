package com.example.news.utils

import android.app.Activity
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.example.news.views.activities.MainActivity

/**
Created by ian
 */

object FullScreen {
    fun setFullScreen (activity: Activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE)
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}