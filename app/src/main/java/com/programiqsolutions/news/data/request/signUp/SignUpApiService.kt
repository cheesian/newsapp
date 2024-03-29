package com.programiqsolutions.news.data.request.signUp

import com.programiqsolutions.news.data.request.URLs.SIGN_UP_ENDPOINT
import com.programiqsolutions.news.data.response.signUp.SignUpResponse
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