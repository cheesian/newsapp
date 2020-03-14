package com.example.news.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.news.data.request.TopHeadlinesApiService
import com.example.news.data.request.URLs.API_KEY
import com.example.news.data.request.URLs.Q
import com.example.news.data.response.everything.AllResponseEntity
import com.example.news.data.response.everything.ArticleResponseEntity
import com.example.news.data.response.everything.ArticlesDao
import com.example.news.data.response.everything.SourceResponseEntity
import io.reactivex.Observable
import javax.inject.Inject


/**
Created by ian
 */

class TopHeadlinesRepository @Inject constructor(
    private val topHeadlinesApiService: TopHeadlinesApiService,
    private val articlesDao: ArticlesDao
) {

    fun getTopHeadlines(country:String, category:String, sources:String, q:String): Observable<AllResponseEntity> {
        return topHeadlinesApiService.getTopHeadlines(API_KEY, country, category, sources, q)
    }

    fun getTopHeadlines(): Observable<AllResponseEntity> {
        return topHeadlinesApiService.getTopHeadlines(API_KEY, Q)
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

    fun deleteAllSources(){
        articlesDao.deleteAllSources()
    }

    fun deleteSource (source: SourceResponseEntity) {
        articlesDao.deleteSource(source)
    }

    fun insertSource (source: SourceResponseEntity){
        articlesDao.insertSource (source)
    }

    fun getSources(): LiveData<List<SourceResponseEntity>> {
        return articlesDao.getSources()
    }
}