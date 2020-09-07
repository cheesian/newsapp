package com.programiqsolutions.news.views.fragments.topHeadlines.viewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.programiqsolutions.news.data.Constants.Q
import com.programiqsolutions.news.data.repositories.TopHeadlinesRepository
import com.programiqsolutions.news.data.response.GeneralResponse
import com.programiqsolutions.news.data.response.topHeadlines.TopHeadlinesResponseEntity
import com.programiqsolutions.news.utils.Notify.log
import com.programiqsolutions.news.utils.Notify.setErrorMessage
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
    var articleList = MutableLiveData<List<TopHeadlinesResponseEntity>>()
    var sourceList = topHeadlinesRepository.getSources()
    var lastRequest = MutableLiveData<HashMap<String, String>>()
    var nextPageList = MutableLiveData<List<TopHeadlinesResponseEntity>>()

    fun getGeneralResponse(): MutableLiveData<GeneralResponse> {
        return generalResponse
    }

    fun consume(generalResponse: GeneralResponse) {

        when (generalResponse.status) {
            GeneralResponse.Status.SUCCESS -> {
                log(message = "Status.SUCCESS")
                visibility.value = View.GONE
                generalResponse.topResponseEntity?.articleResponseEntities?.let { list ->
                    nextPageList.value = with (list) {
                        filter {topHeadlinesResponseEntity ->
//                                Eliminate duplicate articles by checking if the article exists in the stored list first
                            topHeadlinesResponseEntity !in topHeadlinesRepository.getArticleList()
                        }
                        filter {topHeadlinesResponseEntity ->
//                                Eliminate articles with blank source ids
                            !topHeadlinesResponseEntity.sourceResponseEntity?.id.isNullOrBlank()
                        }
                        topHeadlinesRepository.insertArticleList(this)
                        message.value = "Found $size articles"
                        this
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

    fun getTopHeadlines(q: String = Q) {
        val query = HashMap<String, String>()
        query["q"] = q
        recordLastQuery(query)
        disposable.add(
            topHeadlinesRepository.getTopHeadlines(q)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { generalResponse.setValue(GeneralResponse.loading()) }
                .subscribe (
                    { result -> generalResponse.value = GeneralResponse.topResponseSuccess(result) },
                    { error -> generalResponse.value = GeneralResponse.error(error) }
                )
        )
    }

    fun getCustomTopHeadlines(map: HashMap<String, String>) {
        recordLastQuery(map)
        disposable.add(
            topHeadlinesRepository.getCustomTopHeadlines(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { generalResponse.value = GeneralResponse.loading() }
                .subscribe(
                    { result -> generalResponse.value = GeneralResponse.topResponseSuccess(result) },
                    { error -> generalResponse.value = GeneralResponse.error(error) }
                )
        )
    }

    fun getNextPage(map: HashMap<String, String>) {
        recordLastQuery(map)
        disposable.add(
            topHeadlinesRepository.getCustomTopHeadlines(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { visibility.value = View.VISIBLE }
                .subscribe (
                    { result -> generalResponse.value = GeneralResponse.topResponseSuccess(result) },
                    { error -> generalResponse.value = GeneralResponse.error(error) }
                )
        )
    }

    private fun recordLastQuery (hashMap: HashMap<String, String>) {
//        This function helps keep a record of the previous query for the refresh function
        lastRequest.value = hashMap
    }

    fun displayDatabaseArticles() {
//        This function will run when the network connection is off to display the articles from the DB
        articleList.value = topHeadlinesRepository.getArticleList()
    }
}