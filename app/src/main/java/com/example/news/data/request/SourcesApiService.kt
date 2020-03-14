package com.example.news.data.request

import androidx.lifecycle.MutableLiveData
import com.example.news.data.request.URLs.API_KEY
import com.example.news.data.request.URLs.LANGUAGE
import com.example.news.data.request.URLs.SOURCES_ENDPOINT
import com.example.news.data.response.sources.SourcesResponseEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
Created by ian
 */

interface SourcesApiService {

    @GET(SOURCES_ENDPOINT)
    fun getSources(
        @Query("apiKey")
        apiKey:String,
        @Query("country")
        country:String,
        @Query("category")
        category:String,
        @Query("language")
        language:String
    ): Observable<SourcesResponseEntity>
//    country: country_list.xml
//    category: category_list.xml
//    language: languages_list.xml
@GET(SOURCES_ENDPOINT)
fun getSources(
    @Query("apiKey")
    apiKey:String
): Observable<SourcesResponseEntity>
}