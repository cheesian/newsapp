package com.example.news.data.request

import com.example.news.data.request.URLs.GET_USER_ENDPOINT
import com.example.news.data.request.URLs.SIGN_IN_ENDPOINT
import com.example.news.data.request.signIn.SignInRequest
import com.example.news.data.response.signIn.SignInResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


/**
Created by ian
 */

interface SignInApiService {

    @POST(SIGN_IN_ENDPOINT)
    fun signIn(
        @Body
        data: SignInRequest
    ): Observable<SignInResponse>

    @GET(GET_USER_ENDPOINT)
    fun getUser()

}