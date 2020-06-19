package com.example.news.data.request

import com.example.news.data.Constants.API_KEY
import com.example.news.data.request.URLs.EVERYTHING_ENDPOINT
import com.example.news.data.response.everything.AllResponseEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap


/**
Created by ian
 */

interface EverythingApiService {

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
    @Query("sortBy")
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