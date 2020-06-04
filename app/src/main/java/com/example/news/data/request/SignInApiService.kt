package com.example.news.data.request

import com.example.news.data.request.URLs.SIGN_IN_ENDPOINT
import com.example.news.data.request.signIn.SignInRequest
import retrofit2.http.POST


/**
Created by ian
 */

interface SignInApiService {

    @POST(SIGN_IN_ENDPOINT)
    fun signIn(
        data: SignInRequest
    )

}