package com.programiqsolutions.news.data.repositories

import com.programiqsolutions.news.data.daos.AccountDao
import com.programiqsolutions.news.data.request.FeedbackApiService
import com.programiqsolutions.news.data.request.feedback.FeedbackRequest
import javax.inject.Inject

class FeedbackRepository @Inject constructor(
    var accountDao: AccountDao,
    var feedbackApiService: FeedbackApiService
) {

    fun sendFeedback(feedbackRequest: FeedbackRequest) = feedbackApiService.sendFeedback(feedbackRequest)

    fun getUsers() = accountDao.getUsers()

}