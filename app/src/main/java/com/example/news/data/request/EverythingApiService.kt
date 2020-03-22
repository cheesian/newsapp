package com.example.news.data.request

import androidx.lifecycle.MutableLiveData
import com.example.news.data.request.URLs.API_KEY
import com.example.news.data.request.URLs.EVERYTHING_ENDPOINT
import com.example.news.data.request.URLs.LANGUAGE
import com.example.news.data.request.URLs.Q
import com.example.news.data.request.URLs.SORT_BY
import com.example.news.data.response.everything.AllResponseEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap


/**
Created by ian
 */

interface EverythingApiService {

    @GET(EVERYTHING_ENDPOINT)
    fun getEverything(
        @Query("apiKey")
        apiKey:String,
        @Query("q")
        q:String,
        @Query("from")
        from:String,
        @Query("to")
        to:String,
        @Query("language")
        language:String,
        @Query("sort_by")
        sortBy:String
    ): Observable<AllResponseEntity>
//    q: editText input
//    from: date e.g. 2020-02-21
//    to: date e.g. 2020-02-21
//    language: languages_list.xml
//    sort_by: [ popularity, publishedAt ]
@GET(EVERYTHING_ENDPOINT)
fun getEverythingWithoutDates(
    @Query("apiKey")
    apiKey:String = API_KEY,
    @Query("q")
    q:String,
    @Query("language")
    language:String,
    @Query("sort_by")
    sortBy:String
): Observable<AllResponseEntity>

    // for the custom queries
    @GET(EVERYTHING_ENDPOINT)
    fun getCustomEverything(
        @Query("apiKey")
        apiKey:String,
        @Query("pageSize")
        pageSize:Int,
        @Query("sortBy")
        sortBy:String,
        @QueryMap map: HashMap<String, String>
    ): Observable<AllResponseEntity>
}