package com.programiqsolutions.news.data.repositories

import androidx.lifecycle.LiveData
import com.programiqsolutions.news.data.Constants.API_KEY
import com.programiqsolutions.news.data.Constants.LANGUAGE
import com.programiqsolutions.news.data.request.SourcesApiService
import com.programiqsolutions.news.data.response.sources.SourceResponseEntity
import com.programiqsolutions.news.data.daos.SourcesDao
import com.programiqsolutions.news.data.response.sources.SourcesResponseEntity
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

    fun getCustomSources(map: HashMap<String, String>): Observable<SourcesResponseEntity> {
        return sourcesApiService.getCustomSources(API_KEY, map)
    }

    fun getLocalSources(): LiveData<List<SourceResponseEntity>> {
        return sourcesDao.getSources()
    }

    fun getLocalSourceList(): List<SourceResponseEntity> {
        return sourcesDao.getSourceList()
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

    fun insertSourceList(list: List<SourceResponseEntity>) {
        sourcesDao.insertSourceList(list)
    }
}