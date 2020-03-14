package com.example.news.data.response.everything

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "articles", primaryKeys = ["title", "publishedAt"])
data class ArticleResponseEntity(
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