package com.example.news.views.fragments.sources.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.data.repositories.SourcesRepository
import com.example.news.data.request.URLs.LANGUAGE
import com.example.news.data.response.GeneralResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
Created by ian
 */

class SourcesViewModel (
    private val sourcesRepository: SourcesRepository,
    private val generalResponse: MutableLiveData<GeneralResponse> = MutableLiveData()
): ViewModel() {

    private val disposable = CompositeDisposable()

    fun getGeneralResponse(): MutableLiveData<GeneralResponse> {
        return generalResponse
    }

    fun getSources(country:String, category:String, language:String = LANGUAGE) {
        disposable.add(
            sourcesRepository.getSources(country, category, language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { generalResponse.value = GeneralResponse.loading() }
                .subscribe(
                    { result -> generalResponse.value = GeneralResponse.sourcesResponseSuccess(result) },
                    { error -> generalResponse.value = GeneralResponse.error(error) }
                )
        )
    }

    fun getSources() {
        disposable.add(
            sourcesRepository.getSources()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { generalResponse.value = GeneralResponse.loading() }
                .subscribe(
                    { result -> generalResponse.value = GeneralResponse.sourcesResponseSuccess(result) },
                    { error -> generalResponse.value = GeneralResponse.error(error) }
                )
        )
    }
}