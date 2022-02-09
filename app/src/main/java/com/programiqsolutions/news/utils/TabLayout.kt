package com.programiqsolutions.news.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView

object TabLayout {

//    This function sets up a tabLayout with a custom Icon and text below it
//    Ensure you use TabLayout.getTabAt(TabPosition).customView = createTabWithIcon(...)
    fun createTabWithIcon(rootContext: Context, tabTemplate: Int, tabText: String, tabIcon: Int): TextView {
        return with (LayoutInflater.from(rootContext).inflate(tabTemplate, null) as TextView) {
            text = tabText
            setCompoundDrawablesWithIntrinsicBounds(0, tabIcon, 0, 0)
            this
        }

    }

}