package com.programiqsolutions.news.utils

import androidx.room.TypeConverter
import com.programiqsolutions.news.data.response.everything.SourceResponseEntity
import com.google.gson.Gson


/**
Created by ian
 */
class SourceResponseEntityConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromSourceResponse(data: SourceResponseEntity?): String {
        return gson.toJson(data)
    }

    @TypeConverter
    fun toSourceResponse(data: String): SourceResponseEntity {
        return gson.fromJson(data, SourceResponseEntity::class.java)
    }
}