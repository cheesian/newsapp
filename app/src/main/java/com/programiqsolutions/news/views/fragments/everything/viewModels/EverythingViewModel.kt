package com.programiqsolutions.news.views.fragments.everything.viewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.programiqsolutions.news.data.Constants.LANGUAGE
import com.programiqsolutions.news.data.Constants.Q
import com.programiqsolutions.news.data.Constants.SORT_BY
import com.programiqsolutions.news.data.repositories.EverythingRepository
import com.programiqsolutions.news.data.response.GeneralResponse
import com.programiqsolutions.news.data.response.everything.ArticleResponseEntity
import com.programiqsolutions.news.utils.Notify
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
    var articleList = MutableLiveData<List<ArticleResponseEntity>>(everythingRepository.getArticleList())
    var sourceList = everythingRepository.getSources()
    var lastRequest = MutableLiveData<HashMap<String, String>>()
    var nextPageList = MutableLiveData<List<ArticleResponseEntity>>()

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

    fun getEverythingWithoutDates(q: String = Q, language: String = LANGUAGE, sortBy: String = SORT_BY) {
        val query = HashMap<String, String>()
        query["q"] = q
        query["language"] = language
        query["sortBy"] = sortBy
        recordLastQuery(query)
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

    fun getNextPage(map: HashMap<String, String>) {
        recordLastQuery(map)
        disposable.add(
            everythingRepository.getCustomEverything(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { visibility.value = View.VISIBLE }
                .subscribe(
                    { result ->
                        visibility.value = View.GONE
                        result.articleResponseEntities.let {
                            nextPageList.value = with (it) {
                                filter {articleResponseEntity ->
//                                Eliminate duplicate articles by checking if the article exists in the stored list first
                                    articleResponseEntity !in everythingRepository.getArticleList()
                                }
                                everythingRepository.insertArticleList(this)
                                this
                            }
                        }
                    },
                    { error ->
                        generalResponse.value = GeneralResponse.error(error)
                    }
                )
        )
    }

    fun getCustomEverything(map: HashMap<String, String>) {
        recordLastQuery(map)
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

    private fun recordLastQuery (hashMap: HashMap<String, String>) {
//        This function helps keep a record of the previous query for the refresh function
        lastRequest.apply {
            value = hashMap
        }
    }
}