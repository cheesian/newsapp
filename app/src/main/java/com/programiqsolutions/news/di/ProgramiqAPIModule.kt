package com.programiqsolutions.news.di

import com.programiqsolutions.news.data.Constants.PROGRAMIQ_RETROFIT
import com.programiqsolutions.news.data.Constants.PROGRAMIQ_TOKEN_RETROFIT
import com.programiqsolutions.news.data.request.feedback.FeedbackApiService
import com.programiqsolutions.news.data.request.signIn.SignInApiService
import com.programiqsolutions.news.data.request.signUp.SignUpApiService
import com.programiqsolutions.news.data.request.user.UserApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named


/**
Created by ian
 */

@Module
class ProgramiqAPIModule {

    @Provides
    fun provideSignUpApiService(@Named(PROGRAMIQ_RETROFIT) retrofit: Retrofit): SignUpApiService {

        return retrofit.create(SignUpApiService::class.java)

    }

    @Provides
    fun provideSignInApiService(@Named(PROGRAMIQ_RETROFIT) retrofit: Retrofit): SignInApiService {

        return retrofit.create(SignInApiService::class.java)

    }

    @Provides
    fun provideUserApiService(@Named(PROGRAMIQ_TOKEN_RETROFIT) retrofit: Retrofit): UserApiService {

        return retrofit.create(UserApiService::class.java)

    }

    @Provides
    fun provideFeedbackApiService(@Named(PROGRAMIQ_TOKEN_RETROFIT) retrofit: Retrofit): FeedbackApiService {

        return retrofit.create(FeedbackApiService::class.java)

    }

}