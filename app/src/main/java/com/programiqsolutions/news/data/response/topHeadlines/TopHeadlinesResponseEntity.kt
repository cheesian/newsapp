package com.programiqsolutions.news.data.response.topHeadlines

import androidx.room.Entity
import com.programiqsolutions.news.data.response.everything.SourceResponseEntity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "topHeadlines", primaryKeys = ["title", "publishedAt"])
data class TopHeadlinesResponseEntity(
    @SerializedName("author")
    val author: String? = "Anonymous",
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String? = "No description provided",
    @SerializedName("url")
    val url: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("content")
    val content: String? = "No content provided",
    @SerializedName("source")
    val sourceResponseEntity: SourceResponseEntity?
)