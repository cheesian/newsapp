package com.example.news.data.request

import com.example.news.data.request.URLs.SIGN_UP_ENDPOINT
import com.example.news.data.request.signUp.SignUpRequest
import com.google.gson.annotations.SerializedName
import retrofit2.http.POST


/**
Created by ian
 */

interface SignUpApiService {
    @POST(SIGN_UP_ENDPOINT)
    fun signUp(
        data: SignUpRequest
    )
}