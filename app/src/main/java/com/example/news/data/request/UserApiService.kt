package com.example.news.data.request

import com.example.news.data.request.URLs.GET_USER_ENDPOINT
import com.example.news.data.response.signIn.GetUserResponse
import io.reactivex.Observable
import retrofit2.http.GET


/**
Created by ian
 */

interface UserApiService {

    @GET(GET_USER_ENDPOINT)
    fun getUser(): Observable<GetUserResponse>
}