package com.programiqsolutions.news.data.request

import com.programiqsolutions.news.data.request.URLs.SOURCES_ENDPOINT
import com.programiqsolutions.news.data.response.sources.SourcesResponseEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap


/**
Created by ian
 */

interface SourcesApiService {

    @GET(SOURCES_ENDPOINT)
    fun getSources(
        @Query("apiKey")
        apiKey: String,
        @Query("country")
        country: String,
        @Query("category")
        category: String,
        @Query("language")
        language: String
    ): Observable<SourcesResponseEntity>

//    country: country_list.xml
//    category: category_list.xml
//    language: languages_list.xml
    @GET(SOURCES_ENDPOINT)
    fun getSources(
        @Query("apiKey")
        apiKey: String
    ): Observable<SourcesResponseEntity>

    @GET(SOURCES_ENDPOINT)
    fun getCustomSources(
        @Query("apiKey")
        apiKey: String,
        @QueryMap
        map: HashMap<String, String>
    ): Observable<SourcesResponseEntity>
}