package com.example.news.di

import com.example.news.data.request.EverythingApiService
import com.example.news.data.request.SourcesApiService
import com.example.news.data.request.TopHeadlinesApiService
import com.example.news.data.request.URLs
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named


/**
Created by ian
 */

@Module
class NewsAPIModule {

    @Provides
    fun providesSourcesApiService (@Named(URLs.NEWS_RETROFIT) retrofit: Retrofit): SourcesApiService {

        return retrofit.create(SourcesApiService::class.java)

    }

    @Provides
    fun providesEverythingApiService(@Named(URLs.NEWS_RETROFIT) retrofit: Retrofit): EverythingApiService {

        return retrofit.create(EverythingApiService::class.java)

    }

    @Provides
    fun providesTopHeadlinesApiService(@Named(URLs.NEWS_RETROFIT) retrofit: Retrofit): TopHeadlinesApiService {

        return retrofit.create(TopHeadlinesApiService::class.java)

    }

}