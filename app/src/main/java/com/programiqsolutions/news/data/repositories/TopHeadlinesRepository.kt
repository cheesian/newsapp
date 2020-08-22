package com.programiqsolutions.news.data.repositories

import androidx.lifecycle.LiveData
import com.programiqsolutions.news.data.Constants.API_KEY
import com.programiqsolutions.news.data.Constants.PAGE_SIZE
import com.programiqsolutions.news.data.request.TopHeadlinesApiService
import com.programiqsolutions.news.data.daos.TopHeadlinesDao
import com.programiqsolutions.news.data.response.everything.SourceResponseEntity
import com.programiqsolutions.news.data.response.topHeadlines.TopHeadlinesResponseEntity
import com.programiqsolutions.news.data.response.topHeadlines.TopResponseEntity
import io.reactivex.Observable
import javax.inject.Inject


/**
Created by ian
 */

class TopHeadlinesRepository @Inject constructor(
    private val topHeadlinesApiService: TopHeadlinesApiService,
    private val topHeadlinesDao: TopHeadlinesDao
) {

    fun getTopHeadlines(q: String): Observable<TopResponseEntity> {
        return topHeadlinesApiService.getTopHeadlines(API_KEY, PAGE_SIZE, q)
    }

    fun getCustomTopHeadlines(map: HashMap<String, String>): Observable<TopResponseEntity> {
        return topHeadlinesApiService.getCustomTopHeadlines(API_KEY, PAGE_SIZE, map)
    }

    fun getArticles(): LiveData<List<TopHeadlinesResponseEntity>> {
        return topHeadlinesDao.getArticles()
    }

    fun getArticleList(): List<TopHeadlinesResponseEntity> {
        return topHeadlinesDao.getArticleList()
    }

    fun deleteAllArticles(){
        topHeadlinesDao.deleteAllArticles()
    }

    fun deleteArticle(article: TopHeadlinesResponseEntity) {
        topHeadlinesDao.deleteArticle(article)
    }

    fun insertArticle(article: TopHeadlinesResponseEntity){
        topHeadlinesDao.insertArticle(article)
    }

    fun insertArticleList(list: List<TopHeadlinesResponseEntity>){
        topHeadlinesDao.insertArticleList(list)
    }

    fun deleteAllSources(){
        topHeadlinesDao.deleteAllSources()
    }

    fun deleteSource (source: SourceResponseEntity) {
        topHeadlinesDao.deleteSource(source)
    }

    fun insertSource (source: SourceResponseEntity){
        topHeadlinesDao.insertSource (source)
    }

    fun getSources(): LiveData<List<SourceResponseEntity>> {
        return topHeadlinesDao.getSources()
    }
}