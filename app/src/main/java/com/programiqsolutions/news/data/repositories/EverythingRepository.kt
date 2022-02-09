package com.programiqsolutions.news.data.repositories

import androidx.lifecycle.LiveData
import com.programiqsolutions.news.data.Constants.API_KEY
import com.programiqsolutions.news.data.Constants.LANGUAGE
import com.programiqsolutions.news.data.Constants.PAGE_SIZE
import com.programiqsolutions.news.data.Constants.Q
import com.programiqsolutions.news.data.Constants.SORT_BY
import com.programiqsolutions.news.data.request.EverythingApiService
import com.programiqsolutions.news.data.response.everything.AllResponseEntity
import com.programiqsolutions.news.data.response.everything.ArticleResponseEntity
import com.programiqsolutions.news.data.daos.ArticlesDao
import com.programiqsolutions.news.data.response.everything.SourceResponseEntity
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
        return everythingApiService.getCustomEverything(API_KEY, PAGE_SIZE, SORT_BY, map)
    }

    fun getEverythingWithoutDates(q: String, language: String = LANGUAGE, sortBy: String = SORT_BY): Observable<AllResponseEntity>{
        return everythingApiService.getEverythingWithoutDates(API_KEY, q, language, sortBy)
    }

    fun getArticles(): LiveData<List<ArticleResponseEntity>> {
        return articlesDao.getArticles()
    }

    fun getArticleList(): List<ArticleResponseEntity> {
        return articlesDao.getArticleList()
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