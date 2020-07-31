package com.programiqsolutions.news.data.request.feedback

import com.google.gson.annotations.SerializedName

data class FeedbackRequest(
    @SerializedName("comment")
    val comment: Comment,
    @SerializedName("user")
    val user: String
)