package com.example.news.data.request

import androidx.lifecycle.MutableLiveData
import com.example.news.data.request.URLs.API_KEY
import com.example.news.data.request.URLs.TOP_HEADLINES_ENDPOINT
import com.example.news.data.response.everything.AllResponseEntity
import com.example.news.data.response.topHeadlines.TopResponseEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap


/**
Created by ian
 */

interface TopHeadlinesApiService {

//    country: country_list.xml
//    category: category_list.xml
//    sources: generate dynamically
//    q: editText input

    @GET(TOP_HEADLINES_ENDPOINT)
    fun getTopHeadlines(
        @Query("apiKey")
        apiKey:String,
        @Query("pageSize")
        pageSize:Int,
        @Query("q")
        q:String
    ): Observable<TopResponseEntity>

    // for the custom queries
    @GET(TOP_HEADLINES_ENDPOINT)
    fun getCustomTopHeadlines(
        @Query("apiKey")
        apiKey:String,
        @Query("pageSize")
        pageSize:Int,
        @QueryMap map: HashMap<String, String>
    ): Observable<TopResponseEntity>
}