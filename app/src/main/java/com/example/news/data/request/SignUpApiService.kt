package com.example.news.data.request

import com.example.news.data.request.URLs.SIGN_UP_ENDPOINT
import com.example.news.data.request.signUp.SignUpRequest
import com.example.news.data.response.signUp.SignUpResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST


/**
Created by ian
 */

interface SignUpApiService {
    @POST(SIGN_UP_ENDPOINT)
    fun signUp(
        @Body
        data: SignUpRequest
    ): Observable<SignUpResponse>
}