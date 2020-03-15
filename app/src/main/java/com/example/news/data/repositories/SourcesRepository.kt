package com.example.news.data.repositories

import androidx.lifecycle.LiveData
import com.example.news.data.request.SourcesApiService
import com.example.news.data.request.URLs.API_KEY
import com.example.news.data.request.URLs.LANGUAGE
import com.example.news.data.response.sources.SourceResponseEntity
import com.example.news.data.daos.SourcesDao
import com.example.news.data.response.sources.SourcesResponseEntity
import io.reactivex.Observable
import javax.inject.Inject


/**
Created by ian
 */

class SourcesRepository @Inject constructor(
    private val sourcesApiService: SourcesApiService,
    private val sourcesDao: SourcesDao
) {

    fun getSources(country:String, category:String, language:String = LANGUAGE): Observable<SourcesResponseEntity> {
        return sourcesApiService.getSources(API_KEY, country, category, language)
    }

    fun getSources(): Observable<SourcesResponseEntity> {
        return sourcesApiService.getSources(API_KEY)
    }

    fun getLocalSources(): LiveData<List<SourceResponseEntity>> {
        return sourcesDao.getSources()
    }

    fun deleteAllSources() {
        sourcesDao.deleteAllSources()
    }

    fun deleteSource(source: SourceResponseEntity) {
        sourcesDao.deleteSource(source)

    }
    fun insertSource(source: SourceResponseEntity) {
        sourcesDao.insertSource(source)
    }
}