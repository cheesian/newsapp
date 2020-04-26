package com.example.news.data.repositories

import androidx.lifecycle.LiveData
import com.example.news.data.request.EverythingApiService
import com.example.news.data.request.URLs
import com.example.news.data.request.URLs.API_KEY
import com.example.news.data.request.URLs.LANGUAGE
import com.example.news.data.request.URLs.SORT_BY
import com.example.news.data.response.everything.AllResponseEntity
import com.example.news.data.response.everything.ArticleResponseEntity
import com.example.news.data.daos.ArticlesDao
import com.example.news.data.request.URLs.Q
import com.example.news.data.response.everything.SourceResponseEntity
import io.reactivex.Observable
import javax.inject.Inject


/**
Created by ian
 */

class EverythingRepository @Inject constructor(
    private val everythingApiService: EverythingApiService,
    private val articlesDao: ArticlesDao
) {

    fun getCustomEverything(map: HashMap<String, String>): Observable<AllResponseEntity> {
        if (!map.containsKey("q")) map["q"] = Q
        return everythingApiService.getCustomEverything(API_KEY, URLs.PAGE_SIZE, URLs.SORT_BY, map)
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

    fun insertArticleList(list: List<ArticleResponseEntity>) {
        articlesDao.insertArticleList(list)
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