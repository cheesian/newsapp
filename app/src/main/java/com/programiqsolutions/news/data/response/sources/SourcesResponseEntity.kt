package com.programiqsolutions.news.data.response.sources

import com.google.gson.annotations.SerializedName

data class SourcesResponseEntity(
    @SerializedName("status")
    val status: String,
    @SerializedName("sources")
    val sourceResponseEntities: List<SourceResponseEntity>
)