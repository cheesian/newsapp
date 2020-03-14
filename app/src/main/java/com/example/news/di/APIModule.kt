package com.example.news.di

import com.example.news.data.request.EverythingApiService
import com.example.news.data.request.SourcesApiService
import com.example.news.data.request.TopHeadlinesApiService
import com.example.news.data.request.URLs.BASE_URL
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
Created by ian
 */

@Module
class APIModule {

    @Provides
    fun providesSourcesApiService (retrofit: Retrofit): SourcesApiService {

        return retrofit.create(SourcesApiService::class.java)

    }

    @Provides
    fun provideRetrofit (okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    }

    @Provides
    fun provideOkhttp(): OkHttpClient {

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    }

    @Provides
    fun providesEverythingApiService(retrofit: Retrofit): EverythingApiService {

        return retrofit.create(EverythingApiService::class.java)

    }

    @Provides
    fun providesTopHeadlinesApiService(retrofit: Retrofit): TopHeadlinesApiService {

        return retrofit.create(TopHeadlinesApiService::class.java)

    }



}