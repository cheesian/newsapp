package com.example.news.di

import com.example.news.data.request.SignInApiService
import com.example.news.data.request.SignUpApiService
import com.example.news.data.request.URLs
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
    fun provideSignUpApiService(@Named(URLs.PROGRAMIQ_RETROFIT) retrofit: Retrofit): SignUpApiService {

        return retrofit.create(SignUpApiService::class.java)

    }

    @Provides
    fun provideSignInApiService(@Named(URLs.PROGRAMIQ_RETROFIT) retrofit: Retrofit): SignInApiService {

        return retrofit.create(SignInApiService::class.java)

    }

}