package com.example.news.utils

import android.view.View
import android.widget.CheckBox
import com.example.news.utils.Hide.hide
import com.example.news.utils.Show.show

object Checkbox {

    fun connectCheckboxToView(checkBox: View, view: View) {
        checkBox as CheckBox
        if (checkBox.isChecked) {
            show(view)
        } else {
            hide(view)
        }
    }

    fun uncheck(views: ArrayList<CheckBox>) {
        views.forEach { view ->
            view.isChecked = false
        }
    }

}