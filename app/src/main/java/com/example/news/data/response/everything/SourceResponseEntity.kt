package com.example.news.data.response.everything

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "sourceIDs", primaryKeys = ["id"])

data class SourceResponseEntity(

    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String

)