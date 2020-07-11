package com.programiqsolutions.news.views.fragments.feedback

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FeedbackViewModel: ViewModel() {

    val feedback = MutableLiveData<String>()
    val feedBackError = MutableLiveData<String>()
    val spinnerSelectedItem = MutableLiveData<String>("Click & Select the Type of Feedback")
    val message = MutableLiveData<String>()

    fun sendFeedBack() {
        if (feedback.value.isNullOrBlank()){
            feedBackError.value = "Type your feedback here ..."
            message.value = "Type your feedback"
        } else {
            message.value = feedback.value
        }
    }
}