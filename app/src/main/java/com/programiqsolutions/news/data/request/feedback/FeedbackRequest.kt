package com.programiqsolutions.news.data.request.feedback

import com.google.gson.annotations.SerializedName

data class FeedbackRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("text")
    val text: String
)