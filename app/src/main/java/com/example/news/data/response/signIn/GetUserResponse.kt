package com.example.news.data.response.signIn


import com.google.gson.annotations.SerializedName

data class GetUserResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String?,
    @SerializedName("username")
    val name: String,
    @SerializedName("email")
    val email: String
)