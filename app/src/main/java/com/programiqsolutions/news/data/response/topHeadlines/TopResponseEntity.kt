package com.programiqsolutions.news.data.response.topHeadlines

import com.google.gson.annotations.SerializedName

data class TopResponseEntity(
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("articles")
    val articleResponseEntities: List<TopHeadlinesResponseEntity>
)