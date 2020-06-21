package com.programiqsolutions.news.di

import com.programiqsolutions.news.data.Constants.PROGRAMIQ_RETROFIT
import com.programiqsolutions.news.data.Constants.PROGRAMIQ_TOKEN_RETROFIT
import com.programiqsolutions.news.data.request.SignInApiService
import com.programiqsolutions.news.data.request.SignUpApiService
import com.programiqsolutions.news.data.request.UserApiService
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

}