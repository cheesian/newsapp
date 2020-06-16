package com.example.news.data.response

import com.example.news.data.response.everything.AllResponseEntity
import com.example.news.data.response.signIn.GetUserResponse
import com.example.news.data.response.signIn.SignInResponse
import com.example.news.data.response.signUp.SignUpResponse
import com.example.news.data.response.sources.SourcesResponseEntity
import com.example.news.data.response.topHeadlines.TopResponseEntity


/**
Created by ian
 */

class GeneralResponse private constructor(
    val status: Status,
    val allResponseEntity: AllResponseEntity? = null,
    val sourcesResponseEntity: SourcesResponseEntity? = null,
    val error: Throwable? = null,
    val topResponseEntity: TopResponseEntity? = null,
    val signUpResponse: SignUpResponse? = null,
    val signInResponse: SignInResponse? = null,
    val getUserResponse: GetUserResponse? = null
) {

    companion object {
        fun loading(): GeneralResponse {
            return GeneralResponse(status = Status.LOADING)
        }

        fun error(error: Throwable): GeneralResponse {
            return GeneralResponse(status = Status.ERROR, error = error)
        }

        fun allResponseSuccess(allResponseEntity: AllResponseEntity): GeneralResponse {
            return GeneralResponse(status = Status.SUCCESS, allResponseEntity = allResponseEntity)
        }

        fun topResponseSuccess(topResponseEntity: TopResponseEntity?): GeneralResponse {
            return GeneralResponse(status = Status.SUCCESS, topResponseEntity = topResponseEntity)
        }

        fun sourcesResponseSuccess(sourcesResponseEntity: SourcesResponseEntity): GeneralResponse {
            return GeneralResponse(status = Status.SUCCESS, sourcesResponseEntity = sourcesResponseEntity)
        }

        fun signUpResponseSuccess(signUpResponse: SignUpResponse): GeneralResponse {
            return GeneralResponse(status = Status.SUCCESS, signUpResponse = signUpResponse)
        }

        fun signInResponseSuccess(signInResponse: SignInResponse): GeneralResponse {
            return GeneralResponse(status = Status.SUCCESS, signInResponse = signInResponse)
        }

        fun getUserSuccess(userResponse: GetUserResponse): GeneralResponse {
            return GeneralResponse(status = Status.SUCCESS, getUserResponse = userResponse)
        }
    }

    enum class Status {
        SUCCESS,
        LOADING,
        ERROR
    }

}