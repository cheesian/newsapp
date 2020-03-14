package com.example.news.views.fragments.everything.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.data.repositories.EverythingRepository
import com.example.news.data.request.URLs.LANGUAGE
import com.example.news.data.request.URLs.Q
import com.example.news.data.request.URLs.SORT_BY
import com.example.news.data.response.GeneralResponse
import com.example.news.data.response.everything.AllResponseEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
Created by ian
 */

class EverythingViewModel (
    private val everythingRepository: EverythingRepository,
    private val generalResponse: MutableLiveData<GeneralResponse> = MutableLiveData()
): ViewModel() {

    private var allResponse: MutableLiveData<AllResponseEntity> = MutableLiveData()
    private val disposable = CompositeDisposable()

    fun getGeneralResponse(): MutableLiveData<GeneralResponse> {
        return generalResponse
    }

    fun getEverything(q: String = Q, from: String, to: String, language: String = LANGUAGE, sortBy: String = SORT_BY) {
        disposable.add(
            everythingRepository.getEverything(q, from, to, language, sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { generalResponse.value = GeneralResponse.loading() }
                .subscribe(
                    { result -> generalResponse.value = GeneralResponse.allResponseSuccess(result) },
                    { error -> generalResponse.value = GeneralResponse.error(error)}
                )
        )
    }

    fun getEverythingWithoutDates(q: String = Q, language: String = LANGUAGE, sortBy: String = SORT_BY) {
        disposable.add(
            everythingRepository.getEverythingWithoutDates(q, language, sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { generalResponse.value = GeneralResponse.loading() }
                .subscribe(
                    { result -> generalResponse.value = GeneralResponse.allResponseSuccess(result) },
                    { error -> generalResponse.value = GeneralResponse.error(error)}
                )
        )
    }
}