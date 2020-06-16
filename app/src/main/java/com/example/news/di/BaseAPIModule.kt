package com.example.news.di

import com.example.news.BuildConfig
import com.example.news.data.Constants.NEWS_RETROFIT
import com.example.news.data.Constants.PROGRAMIQ_RETROFIT
import com.example.news.data.request.URLs
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named


/**
Created by ian
 */

@Module
class BaseAPIModule {

    @Provides
    fun provideOkhttp(): OkHttpClient {

        val httpLoggingInterceptor = HttpLoggingInterceptor()
//        Only display the detailed HTTP logs when in development
        httpLoggingInterceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.BASIC
        }

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    }

    @Provides
    @Named(NEWS_RETROFIT)
    fun provideNewsRetrofit (okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(URLs.NEWS_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    }

    @Provides
    @Named(PROGRAMIQ_RETROFIT)
    fun provideProgramiqRetrofit (okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(URLs.PROGRAMIQ_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    }

}