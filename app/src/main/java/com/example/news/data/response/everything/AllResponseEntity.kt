package com.example.news.data.response.everything

import com.google.gson.annotations.SerializedName

data class AllResponseEntity(
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("articles")
    val articleResponseEntities: List<ArticleResponseEntity>
)