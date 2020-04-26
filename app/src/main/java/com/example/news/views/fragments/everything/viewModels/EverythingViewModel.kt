package com.example.news.views.fragments.everything.viewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.data.repositories.EverythingRepository
import com.example.news.data.request.URLs.LANGUAGE
import com.example.news.data.request.URLs.Q
import com.example.news.data.request.URLs.SORT_BY
import com.example.news.data.response.GeneralResponse
import com.example.news.data.response.everything.AllResponseEntity
import com.example.news.utils.Notify
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

    private val disposable = CompositeDisposable()
    var message: MutableLiveData<String> = MutableLiveData()
    var visibility: MutableLiveData<Int> = MutableLiveData()
    var articleList = everythingRepository.getArticles()
    var sourceList = everythingRepository.getSources()

    fun getGeneralResponse(): MutableLiveData<GeneralResponse> {
        return generalResponse
    }

    fun consume(generalResponse: GeneralResponse) {

        when (generalResponse.status) {
            GeneralResponse.Status.SUCCESS -> {
                Notify.log(message = "Status.SUCCESS")
                visibility.value = View.GONE
                generalResponse.allResponseEntity?.articleResponseEntities?.let { list ->
                    everythingRepository.insertArticleList(list)
                    for (entity in list) {
                        entity.sourceResponseEntity?.let {
                            if (!it.id.isNullOrBlank())
                                everythingRepository.insertSource(it)
                        }
                    }
                    val size = list.size
                    message.value = "Found $size articles"
                }
            }

            GeneralResponse.Status.LOADING -> {
                Notify.log(message = "Status.LOADING")
                visibility.value = View.VISIBLE
                message.value = "Fetching data ..."
            }

            GeneralResponse.Status.ERROR -> {
                Notify.log(message = "Status.ERROR")
                visibility.value = View.GONE
                Notify.setErrorMessage(generalResponse.error!!, message)
            }
        }
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
                .doOnSubscribe { generalResponse.setValue(GeneralResponse.loading()) }
                .subscribe(
                    { result -> generalResponse.value = GeneralResponse.allResponseSuccess(result) },
                    { error -> generalResponse.value = GeneralResponse.error(error)}
                )
        )
    }

    fun getCustomEverything(map: HashMap<String, String>) {
        disposable.add(
            everythingRepository.getCustomEverything(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { generalResponse.value = GeneralResponse.loading() }
                .subscribe(
                    { result ->
                        if (!result.articleResponseEntities.isNullOrEmpty())
                            everythingRepository.deleteAllArticles()
                        generalResponse.value = GeneralResponse.allResponseSuccess(result)
                    },
                    { error ->
                        generalResponse.value = GeneralResponse.error(error)
                    }
                )
        )
    }
}