package com.programiqsolutions.news.views.fragments.feedback

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FeedbackViewModel: ViewModel() {

    val feedback = MutableLiveData<String>()
    val feedBackError = MutableLiveData<String>()
    val spinnerSelectedItem = MutableLiveData<String>("Click & Select the Type of Feedback")
    val spinnerError = MutableLiveData<String>()
    val message = MutableLiveData<String>()

    fun sendFeedBack() {
        resetErrors()
        when {
            spinnerSelectedItem.value == "Click & Select the Type of Feedback" -> {
                spinnerError.value = "Click & Select"
            }
            feedback.value.isNullOrBlank() -> {
                feedBackError.value = "Type your feedback here ..."
                message.value = "Type your feedback"
            }
            else -> {
                message.value = "Coming soon"
            }
        }
    }

    private fun resetErrors(){
        spinnerError.value = null
        feedBackError.value = null
    }
}