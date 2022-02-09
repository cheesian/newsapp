package com.programiqsolutions.news.data.request.feedback

import com.programiqsolutions.news.data.request.URLs.FEEDBACK_ENDPOINT
import com.programiqsolutions.news.data.response.signUp.SignUpResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface FeedbackApiService {

    @POST(FEEDBACK_ENDPOINT)
    fun sendFeedback(
        @Body
        data: FeedbackRequest
    ): Observable<SignUpResponse>

}