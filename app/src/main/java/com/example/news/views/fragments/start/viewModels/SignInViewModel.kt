package com.example.news.views.fragments.start.viewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.data.repositories.AccountRepository
import com.example.news.data.request.signIn.SignInRequest
import com.example.news.data.response.GeneralResponse
import com.example.news.utils.Notify
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
Created by ian
 */

class SignInViewModel (
    val accountRepository: AccountRepository
): ViewModel() {

    var generalResponse = MutableLiveData<GeneralResponse>()
    var compositeDisposable = CompositeDisposable()
    var progressBarVisibility = MutableLiveData<Int>()
    var message = MutableLiveData<String>()
    var token = MutableLiveData<String>()

    fun signIn(data: SignInRequest){
        compositeDisposable.add(
            accountRepository.signIn(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { generalResponse.value = GeneralResponse.loading() }
                .subscribe(
                    {
                        generalResponse.value = GeneralResponse.signInResponseSuccess(it)
                    },
                    {
                        generalResponse.value = GeneralResponse.error(it)
                    }
                )
        )
    }

    fun getUser() {

    }

    fun consumeResponse(generalResponse: GeneralResponse) {
        when (generalResponse.status){

            GeneralResponse.Status.LOADING -> {
                progressBarVisibility.value = View.VISIBLE
            }

            GeneralResponse.Status.SUCCESS -> {
                progressBarVisibility.value = View.GONE
                generalResponse.signInResponse?.let {
                    if (it.success) {
//                        Store the token and look at the expiry date
                        token.value = it.token
                    } else {
                        message.value = it.message
                    }
                }
            }

            GeneralResponse.Status.ERROR -> {
                progressBarVisibility.value = View.GONE
                Notify.setErrorMessage(
                    error = generalResponse.error!!,
                    errorMessageVariable = message
                )
            }
        }
    }
}