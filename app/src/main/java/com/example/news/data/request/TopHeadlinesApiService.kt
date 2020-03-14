package com.example.news.data.request

import androidx.lifecycle.MutableLiveData
import com.example.news.data.request.URLs.API_KEY
import com.example.news.data.request.URLs.TOP_HEADLINES_ENDPOINT
import com.example.news.data.response.everything.AllResponseEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
Created by ian
 */

interface TopHeadlinesApiService {

    @GET(TOP_HEADLINES_ENDPOINT)
    fun getTopHeadlines(
        @Query("apiKey")
        apiKey:String,
        @Query("country")
        country:String,
        @Query("category")
        category:String,
        @Query("sources")
        sources:String,
        @Query("q")
        q:String
    ): Observable<AllResponseEntity>
//    country: country_list.xml
//    category: category_list.xml
//    sources: generate dynamically
//    q: editText input

    @GET(TOP_HEADLINES_ENDPOINT)
    fun getTopHeadlines(
        @Query("apiKey")
        apiKey:String,
        @Query("q")
        q:String
    ): Observable<AllResponseEntity>
}