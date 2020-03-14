package com.example.news.utils

import android.view.View
import android.widget.CheckBox
import com.example.news.utils.Hide.hide
import com.example.news.utils.Show.show

object Checkbox {

    fun connectCheckboxToView(checkBox: View, spinner: View, spinnerParent: View) {
        checkBox as CheckBox
        if (checkBox.isChecked) {
            show(spinnerParent)
            show(spinner)
        } else {
            hide(spinner)
        }
    }

    fun uncheck(views: ArrayList<CheckBox>) {
        views.forEach { view ->
            view.isChecked = false
        }
    }

}