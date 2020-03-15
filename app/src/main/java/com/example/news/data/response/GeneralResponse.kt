package com.example.news.data.response

import com.example.news.data.response.everything.AllResponseEntity
import com.example.news.data.response.sources.SourcesResponseEntity
import com.example.news.data.response.topHeadlines.TopResponseEntity


/**
Created by ian
 */

class GeneralResponse private constructor(
    val status: Status,
    val allResponseEntity: AllResponseEntity?,
    val sourcesResponseEntity: SourcesResponseEntity?,
    val error: Throwable?,
    val topResponseEntity: TopResponseEntity?
) {

    companion object {
        fun loading(): GeneralResponse {
            return GeneralResponse(Status.LOADING, null, null, null, null)
        }

        fun error(error: Throwable): GeneralResponse {
            return GeneralResponse(Status.ERROR, null, null, error, null)
        }

        fun allResponseSuccess(allResponseEntity: AllResponseEntity): GeneralResponse {
            return GeneralResponse(Status.SUCCESS, allResponseEntity, null, null, null)
        }

        fun topResponseSuccess(topResponseEntity: TopResponseEntity?): GeneralResponse {
            return GeneralResponse(Status.SUCCESS, null, null, null, topResponseEntity)
        }

        fun sourcesResponseSuccess(sourcesResponseEntity: SourcesResponseEntity): GeneralResponse {
            return GeneralResponse(Status.SUCCESS, null, sourcesResponseEntity, null, null)
        }
    }

    enum class Status {
        SUCCESS,
        LOADING,
        ERROR
    }

}