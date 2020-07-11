package com.programiqsolutions.news.adapters

import android.graphics.drawable.Drawable
import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.programiqsolutions.news.R

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("app:mutableError")
    fun setMutableError(view: View, error: String?) {
        view as TextInputLayout
        view.isErrorEnabled
        view.error = error
    }

    @JvmStatic
    @BindingAdapter("app:mutableFeedbackIcon")
    fun setMutableFeedbackIcon(view: View, feedback: String?) {
        view as TextInputLayout
        val context = view.context
        val icon: Drawable
        val contentDesc: String
        when (feedback) {
            "Positive feedback" -> {
                icon = context.getDrawable(R.drawable.ic_happy)!!
                contentDesc = "Happy"
            }
            "Negative feedback" -> {
                icon = context.getDrawable(R.drawable.ic_disappointed)!!
                contentDesc = "Disappointed"
            }
            else -> {
                icon = context.getDrawable(R.drawable.ic_light_bulb)!!
                contentDesc = "Idea"
            }
        }
        view.startIconDrawable = icon
        view.startIconContentDescription = contentDesc
    }

}