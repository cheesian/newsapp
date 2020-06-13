package com.example.news.data.repositories

import com.example.news.data.request.SignInApiService
import com.example.news.data.request.SignUpApiService
import com.example.news.data.request.signIn.SignInRequest
import com.example.news.data.request.signUp.SignUpRequest
import com.example.news.data.response.signIn.SignInResponse
import com.example.news.data.response.signUp.SignUpResponse
import io.reactivex.Observable
import javax.inject.Inject


/**
Created by ian
 */

class AccountRepository @Inject constructor(
    var signInApiService: SignInApiService,
    var signUpApiService: SignUpApiService
){
    fun signUp(data: SignUpRequest): Observable<SignUpResponse> {
        return signUpApiService.signUp(data)
    }

    fun signIn(data: SignInRequest): Observable<SignInResponse> {
        return signInApiService.signIn(data)
    }
}