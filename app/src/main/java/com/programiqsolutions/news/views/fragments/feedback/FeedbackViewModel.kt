package com.programiqsolutions.news.views.fragments.feedback

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.programiqsolutions.news.data.Constants.FEEDBACK_CONGRATS_OPTION
import com.programiqsolutions.news.data.Constants.FEEDBACK_CONGRATS_VALUE
import com.programiqsolutions.news.data.Constants.FEEDBACK_DEFAULT_OPTION
import com.programiqsolutions.news.data.Constants.FEEDBACK_ERROR_OPTION
import com.programiqsolutions.news.data.Constants.FEEDBACK_ERROR_VALUE
import com.programiqsolutions.news.data.Constants.FEEDBACK_SUGGESTION_OPTION
import com.programiqsolutions.news.data.Constants.FEEDBACK_SUGGESTION_VALUE
import com.programiqsolutions.news.data.repositories.FeedbackRepository
import com.programiqsolutions.news.data.request.feedback.FeedbackRequest
import com.programiqsolutions.news.data.response.GeneralResponse
import com.programiqsolutions.news.utils.Notify.setErrorMessage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FeedbackViewModel (
    var feedbackRepository: FeedbackRepository
): ViewModel() {

    val feedback = MutableLiveData<String>()
    val feedBackError = MutableLiveData<String>()
    val spinnerSelectedItem = MutableLiveData<String>(FEEDBACK_DEFAULT_OPTION)
    val spinnerError = MutableLiveData<String>()
    val message = MutableLiveData<String>()
    val disposable = CompositeDisposable()
    val response = MutableLiveData<GeneralResponse>()
    val progressBarVisibility = MutableLiveData<Int>(View.GONE)

    private fun sendFeedbackToApi(){
        disposable.add(
            feedbackRepository.sendFeedback(buildFeedbackRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { response.value = GeneralResponse.loading() }
                .subscribe(
                    {
                        response.value = GeneralResponse.signUpResponseSuccess(it)
                    },
                    {
                        response.value = GeneralResponse.error(it)
                    }
                )
        )
    }
    fun sendFeedBack() {
        resetErrors()
        when {
            spinnerSelectedItem.value == FEEDBACK_DEFAULT_OPTION -> {
                spinnerError.value = "Click & Select"
            }
            feedback.value.isNullOrBlank() -> {
                feedBackError.value = "Type your feedback here ..."
                message.value = "Type your feedback"
            }
            else -> {
                sendFeedbackToApi()
            }
        }
    }

    private fun resetErrors(){
        spinnerError.value = null
        feedBackError.value = null
    }

    private fun buildFeedbackRequest(): FeedbackRequest {
        return FeedbackRequest(
            email = feedbackRepository.getUsers()[0].email,
            text = feedback.value!!,
            type = when (spinnerSelectedItem.value!!) {
                FEEDBACK_CONGRATS_OPTION -> FEEDBACK_CONGRATS_VALUE
                FEEDBACK_ERROR_OPTION -> FEEDBACK_ERROR_VALUE
                FEEDBACK_SUGGESTION_OPTION -> FEEDBACK_SUGGESTION_VALUE
                else -> ""
            }
        )
    }

    fun consumeResponse(generalResponse: GeneralResponse) {
        when (generalResponse.status) {
            GeneralResponse.Status.LOADING -> {
                progressBarVisibility.value = View.VISIBLE
            }
            GeneralResponse.Status.SUCCESS -> {
                progressBarVisibility.value = View.GONE
                message.value = "Feedback Sent. Thank you"
            }
            GeneralResponse.Status.ERROR -> {
                progressBarVisibility.value = View.GONE
                setErrorMessage(generalResponse.error!!, message)
            }
        }
    }
}