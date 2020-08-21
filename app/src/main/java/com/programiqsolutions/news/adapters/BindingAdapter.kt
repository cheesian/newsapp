package com.programiqsolutions.news.adapters

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.programiqsolutions.news.R
import com.programiqsolutions.news.data.Constants
import com.programiqsolutions.news.data.Constants.FEEDBACK_CONGRATS_OPTION
import com.programiqsolutions.news.data.Constants.FEEDBACK_ERROR_OPTION

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("app:progressBarVisibility")
    fun setProgressBarVisibility(view: View, visibility: Int?) {
        view as ProgressBar
        view.visibility = visibility ?: View.GONE
    }

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
            FEEDBACK_CONGRATS_OPTION -> {
                icon = context.getDrawable(R.drawable.ic_happy)!!
                contentDesc = "Happy"
            }
            FEEDBACK_ERROR_OPTION -> {
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