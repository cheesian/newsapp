package com.example.news.data.response.signIn


import com.google.gson.annotations.SerializedName

data class GetUserResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)