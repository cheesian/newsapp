package com.example.news.data.response

import com.example.news.data.response.everything.AllResponseEntity
import com.example.news.data.response.sources.SourcesResponseEntity


/**
Created by ian
 */

class GeneralResponse private constructor(
    val status: Status,
    val allResponseEntity: AllResponseEntity?,
    val sourcesResponseEntity: SourcesResponseEntity?,
    val error: Throwable?
) {

    companion object {
        fun loading(): GeneralResponse {
            return GeneralResponse(Status.LOADING, null, null, null)
        }

        fun error(error: Throwable): GeneralResponse {
            return GeneralResponse(Status.ERROR, null, null, error)
        }

        fun allResponseSuccess(allResponseEntity: AllResponseEntity): GeneralResponse {
            return GeneralResponse(Status.SUCCESS, allResponseEntity, null, null)
        }

        fun sourcesResponseSuccess(sourcesResponseEntity: SourcesResponseEntity): GeneralResponse {
            return GeneralResponse(Status.SUCCESS, null, sourcesResponseEntity, null)
        }
    }

    enum class Status {
        SUCCESS,
        LOADING,
        ERROR
    }

}