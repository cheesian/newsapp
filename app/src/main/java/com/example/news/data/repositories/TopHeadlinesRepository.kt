package com.example.news.data.repositories

import androidx.lifecycle.LiveData
import com.example.news.data.request.TopHeadlinesApiService
import com.example.news.data.request.URLs.API_KEY
import com.example.news.data.request.URLs.PAGE_SIZE
import com.example.news.data.request.URLs.Q
import com.example.news.data.response.everything.AllResponseEntity
import com.example.news.data.response.everything.ArticleResponseEntity
import com.example.news.data.daos.TopHeadlinesDao
import com.example.news.data.response.everything.SourceResponseEntity
import com.example.news.data.response.topHeadlines.TopHeadlinesResponseEntity
import com.example.news.data.response.topHeadlines.TopResponseEntity
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