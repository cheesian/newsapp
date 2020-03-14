package com.example.news.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.news.data.request.EverythingApiService
import com.example.news.data.request.URLs.API_KEY
import com.example.news.data.request.URLs.LANGUAGE
import com.example.news.data.request.URLs.Q
import com.example.news.data.request.URLs.SORT_BY
import com.example.news.data.response.everything.AllResponseEntity
import com.example.news.data.response.everything.ArticleResponseEntity
import com.example.news.data.response.everything.ArticlesDao
import io.reactivex.Observable
import javax.inject.Inject


/**
Created by ian
 */

class EverythingRepository @Inject constructor(
    private val everythingApiService: EverythingApiService,
    private val articlesDao: ArticlesDao
) {

    fun getEverything(q: String, from: String, to: String, language: String, sortBy: String): Observable<AllResponseEntity>{
        return everythingApiService.getEverything(API_KEY, q, from, to, language, sortBy)
    }

    fun getEverythingWithoutDates(q: String, language: String = LANGUAGE, sortBy: String = SORT_BY): Observable<AllResponseEntity>{
        return everythingApiService.getEverythingWithoutDates(API_KEY, q, language, sortBy)
    }

    fun getArticles(): LiveData<List<ArticleResponseEntity>> {
        return articlesDao.getArticles()
    }

    fun deleteAllArticles(){
        articlesDao.deleteAllArticles()
    }

    fun deleteArticle(article: ArticleResponseEntity) {
        articlesDao.deleteArticle(article)
    }

    fun insertArticle(article: ArticleResponseEntity){
        articlesDao.insertArticle(article)
    }

}