package com.example.news.views.fragments.topHeadlines.viewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.data.repositories.TopHeadlinesRepository
import com.example.news.data.request.URLs.Q
import com.example.news.data.response.GeneralResponse
import com.example.news.utils.Notify.log
import com.example.news.utils.Notify.setErrorMessage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
Created by ian
 */

class TopHeadlinesViewModel(
    private val topHeadlinesRepository: TopHeadlinesRepository,
    private val generalResponse: MutableLiveData<GeneralResponse> = MutableLiveData()
) : ViewModel() {

    private val disposable = CompositeDisposable()
    var message: MutableLiveData<String> = MutableLiveData()
    var visibility: MutableLiveData<Int> = MutableLiveData()
    var articleList = topHeadlinesRepository.getArticles()
    var sourceList = topHeadlinesRepository.getSources()

    fun getGeneralResponse(): MutableLiveData<GeneralResponse> {
        return generalResponse
    }

    fun consume(generalResponse: GeneralResponse) {

        when (generalResponse.status) {
            GeneralResponse.Status.SUCCESS -> {
                log(message = "Status.SUCCESS")
                visibility.value = View.GONE
                generalResponse.allResponseEntity?.articleResponseEntities?.let { list ->
                    for (entity in list) {
                        topHeadlinesRepository.insertArticle(entity)
                        entity.sourceResponseEntity?.let {
                            topHeadlinesRepository.insertSource(it)
                        }
                    }
                }
            }

            GeneralResponse.Status.LOADING -> {
                log(message = "Status.LOADING")
                visibility.value = View.VISIBLE
                message.value = "Fetching data ..."
            }

            GeneralResponse.Status.ERROR -> {
                log(message = "Status.ERROR")
                visibility.value = View.GONE
                setErrorMessage(generalResponse.error!!, message)
            }
        }
    }

    fun getTopHeadlines(country:String, category:String, sources:String, q:String = Q) {
        disposable.add(
            topHeadlinesRepository.getTopHeadlines(country, category, sources, q)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { generalResponse.setValue(GeneralResponse.loading()) }
                .subscribe (
                    { result -> generalResponse.value = GeneralResponse.allResponseSuccess(result) },
                    { error -> generalResponse.value = GeneralResponse.error(error) }
                )
        )
    }

    fun getTopHeadlines() {
        disposable.add(
            topHeadlinesRepository.getTopHeadlines()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { generalResponse.setValue(GeneralResponse.loading()) }
                .subscribe (
                    { result -> generalResponse.value = GeneralResponse.allResponseSuccess(result) },
                    { error -> generalResponse.value = GeneralResponse.error(error) }
                )
        )
    }
}