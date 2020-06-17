package com.example.news.views.fragments.start.viewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.data.entities.UserEntity
import com.example.news.data.repositories.AccountRepository
import com.example.news.data.request.signIn.SignInRequest
import com.example.news.data.response.GeneralResponse
import com.example.news.utils.Notify.setErrorMessage
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

    fun signIn(data: SignInRequest){
        accountRepository.deleteAllAccounts()
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
        compositeDisposable.add(
            accountRepository.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { generalResponse.value = GeneralResponse.loading() }
                .subscribe(
                    {
                        generalResponse.value = GeneralResponse.getUserSuccess(it)
                    },
                    {
                        generalResponse.value = GeneralResponse.error(it)
                    }
                )
        )
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
                        accountRepository.insertUser(UserEntity(programiq_token = it.token))
                        val ext = accountRepository.getUsers()
                        getUser()
                    } else {
                        message.value = it.message
                    }
                }

                generalResponse.getUserResponse?.let {
                    message.value = if (it.success) {
                        "Welcome back " + it.name
                    } else {
                        "Something went wrong. Please try again"
                    }
                }
            }

            GeneralResponse.Status.ERROR -> {
                progressBarVisibility.value = View.GONE
                setErrorMessage(
                    error = generalResponse.error!!,
                    errorMessageVariable = message
                )
            }
        }
    }
}