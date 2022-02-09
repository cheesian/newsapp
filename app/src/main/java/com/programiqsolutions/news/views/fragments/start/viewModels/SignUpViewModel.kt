package com.programiqsolutions.news.views.fragments.start.viewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.programiqsolutions.news.data.repositories.AccountRepository
import com.programiqsolutions.news.data.request.signUp.SignUpRequest
import com.programiqsolutions.news.data.response.GeneralResponse
import com.programiqsolutions.news.utils.Notify.setErrorMessage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
Created by ian
 */

class SignUpViewModel (
    val accountRepository: AccountRepository
): ViewModel(){

    var generalResponse = MutableLiveData<GeneralResponse>()
    var compositeDisposable = CompositeDisposable()
    var progressBarVisibility = MutableLiveData<Int>()
    var message = MutableLiveData<String>()

    fun signUp(data: SignUpRequest) {
        compositeDisposable.add(
            accountRepository.signUp(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { generalResponse.value = GeneralResponse.loading() }
                .subscribe(
                    {
                        generalResponse.value = GeneralResponse.signUpResponseSuccess(it)
                    },
                    {
                        generalResponse.value = GeneralResponse.error(it)
                    }
                )
        )
    }
    
    fun consumeResponse(generalResponse: GeneralResponse) {
        when (generalResponse.status) {

            GeneralResponse.Status.LOADING -> {
                progressBarVisibility.value = View.VISIBLE
            }

            GeneralResponse.Status.SUCCESS -> {
                progressBarVisibility.value = View.GONE
                generalResponse.signUpResponse?.let { 
                    if (it.success) {
                        message.value = "Account created successfully"
                    } else {
                        message.value = it.message
                    }
                }
            }

            GeneralResponse.Status.ERROR -> {
                progressBarVisibility.value = View.GONE
                setErrorMessage(generalResponse.error!!, message)
            }
        }
    }
}