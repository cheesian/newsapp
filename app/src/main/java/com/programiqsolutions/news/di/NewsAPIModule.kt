package com.programiqsolutions.news.di

import com.programiqsolutions.news.data.Constants.NEWS_RETROFIT
import com.programiqsolutions.news.data.request.EverythingApiService
import com.programiqsolutions.news.data.request.SourcesApiService
import com.programiqsolutions.news.data.request.TopHeadlinesApiService
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
    fun providesSourcesApiService (@Named(NEWS_RETROFIT) retrofit: Retrofit): SourcesApiService {

        return retrofit.create(SourcesApiService::class.java)

    }

    @Provides
    fun providesEverythingApiService(@Named(NEWS_RETROFIT) retrofit: Retrofit): EverythingApiService {

        return retrofit.create(EverythingApiService::class.java)

    }

    @Provides
    fun providesTopHeadlinesApiService(@Named(NEWS_RETROFIT) retrofit: Retrofit): TopHeadlinesApiService {

        return retrofit.create(TopHeadlinesApiService::class.java)

    }

}