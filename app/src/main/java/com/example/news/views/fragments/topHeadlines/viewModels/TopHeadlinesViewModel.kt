package com.example.news.views.fragments.topHeadlines.viewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.data.repositories.TopHeadlinesRepository
import com.example.news.data.request.URLs.Q
import com.example.news.data.response.GeneralResponse
import com.example.news.data.response.everything.ArticleResponseEntity
import com.example.news.data.response.topHeadlines.TopHeadlinesResponseEntity
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
                    topHeadlinesRepository.insertArticleList(list)
                    for (entity in list) {
                        entity.sourceResponseEntity?.let {
                            if (!it.id.isNullOrBlank())
                            topHeadlinesRepository.insertSource(it)
                        }
                    }
                    val size = list.size
                    message.value = "Found $size articles"
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
                    { result ->
                        if (!result.articleResponseEntities.isNullOrEmpty())
                            topHeadlinesRepository.deleteAllArticles()
                        generalResponse.value = GeneralResponse.topResponseSuccess(result)
                    },
                    { error ->
                        generalResponse.value = GeneralResponse.error(error)
                    }
                )
        )
    }

    fun getNextPage(map: HashMap<String, String>) {
//        This function will not store data using ROOM
//        The purpose is to save on memory usage
        recordLastQuery(map)
        disposable.add(
            topHeadlinesRepository.getCustomTopHeadlines(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { visibility.value = View.VISIBLE }
                .subscribe(
                    { result ->
                        visibility.value = View.GONE
                        result.articleResponseEntities.let {
                            nextPageList.value = it
                        }
                    },
                    { error ->
                        generalResponse.value = GeneralResponse.error(error)
                    }
                )
        )
    }

    private fun recordLastQuery (hashMap: HashMap<String, String>) {
//        This function helps keep a record of the previous query for the refresh function
        lastRequest.apply {
            value = hashMap
        }
    }
}