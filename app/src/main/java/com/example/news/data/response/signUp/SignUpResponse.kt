package com.example.news.data.response.signUp


import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean
)