package com.example.news.data.response.sources

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "sources")
data class SourceResponseEntity(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("url")
    val url: String? = "",
    @SerializedName("category")
    val category: String? = "",
    @SerializedName("language")
    val language: String? = "",
    @SerializedName("country")
    val country: String? = ""
)