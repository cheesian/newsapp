package com.example.news.data.request.signIn


import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("remember_me")
    val rememberMe: Boolean = true
)