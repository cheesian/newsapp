package com.programiqsolutions.news.data.request

import com.programiqsolutions.news.data.request.URLs.SIGN_IN_ENDPOINT
import com.programiqsolutions.news.data.request.signIn.SignInRequest
import com.programiqsolutions.news.data.response.signIn.SignInResponse
import io.reactivex.Observable
import retrofit2.http.Body
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

}