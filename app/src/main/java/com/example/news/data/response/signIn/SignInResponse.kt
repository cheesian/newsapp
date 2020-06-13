package com.example.news.data.response.signIn


import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("expires_at")
    val expiresAt: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("token")
    val token: String,
    @SerializedName("token_type")
    val tokenType: String
)