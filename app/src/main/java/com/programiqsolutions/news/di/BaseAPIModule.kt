package com.programiqsolutions.news.di

import com.programiqsolutions.news.BuildConfig
import com.programiqsolutions.news.data.Constants.SIMPLE_OKHTTP
import com.programiqsolutions.news.data.Constants.NEWS_RETROFIT
import com.programiqsolutions.news.data.Constants.PROGRAMIQ_RETROFIT
import com.programiqsolutions.news.data.Constants.PROGRAMIQ_TOKEN_OKHTTP
import com.programiqsolutions.news.data.Constants.PROGRAMIQ_TOKEN_RETROFIT
import com.programiqsolutions.news.data.daos.AccountDao
import com.programiqsolutions.news.data.request.URLs
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
    fun provideInterceptor():HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
//        Only display the detailed HTTP logs when in development
        httpLoggingInterceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.BASIC
        }
        return httpLoggingInterceptor
    }

    @Provides
    @Named(SIMPLE_OKHTTP)
    fun provideOkhttp(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    }

    @Provides
    @Named(PROGRAMIQ_TOKEN_OKHTTP)
    fun provideTokenOkhttp(httpLoggingInterceptor: HttpLoggingInterceptor, accountDao: AccountDao): OkHttpClient {

        return OkHttpClient.Builder().apply {
            addInterceptor{
                val req = it.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("X-Requested-With", "XMLHttpRequest")
                    .addHeader("Authorization",  "Bearer " + accountDao.getUsers()[0].programiq_token)
                    .build()
                it.proceed(req)
            }
            addInterceptor(httpLoggingInterceptor)
        }.build()
    }

    @Provides
    @Named(NEWS_RETROFIT)
    fun provideNewsRetrofit (@Named(SIMPLE_OKHTTP) okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(URLs.NEWS_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    }

    @Provides
    @Named(PROGRAMIQ_RETROFIT)
    fun provideProgramiqRetrofit (@Named(SIMPLE_OKHTTP) okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(URLs.PROGRAMIQ_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    }

    @Provides
    @Named(PROGRAMIQ_TOKEN_RETROFIT)
    fun provideProgramiqTokenRetrofit (@Named(PROGRAMIQ_TOKEN_OKHTTP) okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(URLs.PROGRAMIQ_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    }

}