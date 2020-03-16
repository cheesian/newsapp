package com.example.news.views.fragments.sources.viewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.data.repositories.SourcesRepository
import com.example.news.data.request.URLs.LANGUAGE
import com.example.news.data.response.GeneralResponse
import com.example.news.utils.Notify
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
    var message: MutableLiveData<String> = MutableLiveData()
    var visibility: MutableLiveData<Int> = MutableLiveData()
    var sourceList = sourcesRepository.getLocalSources()
    var isInitialized: Boolean = false

    fun initialize () {
        isInitialized = true
    }

    fun getGeneralResponse(): MutableLiveData<GeneralResponse> {
        return generalResponse
    }

    fun consume(generalResponse: GeneralResponse) {

        when (generalResponse.status) {
            GeneralResponse.Status.LOADING -> {
                Notify.log(message = "Status.LOADING")
                visibility.value = View.VISIBLE
                message.value = "Fetching data ..."
            }
            GeneralResponse.Status.SUCCESS -> {
                Notify.log(message = "Status.SUCCESS")
                visibility.value = View.GONE
                generalResponse.sourcesResponseEntity?.sourceResponseEntities?.let {list->
                    for (entity in list) {
                        sourcesRepository.insertSource(entity)
                    }
                    val size = list.size
                    message.value = "Found $size sources"
                }
            }
            GeneralResponse.Status.ERROR -> {
                Notify.log(message = "Status.ERROR")
                visibility.value = View.GONE
                Notify.setErrorMessage(generalResponse.error!!, message)
            }
        }
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

    fun getCustomSources(map: HashMap<String, String>) {
        disposable.add(
            sourcesRepository.getCustomSources(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { generalResponse.value = GeneralResponse.loading() }
                .subscribe(
                    {
                        result -> generalResponse.value = GeneralResponse.sourcesResponseSuccess(result)
                    },
                    {
                        error -> generalResponse.value = GeneralResponse.error(error)
                    }
                )
        )
    }
}