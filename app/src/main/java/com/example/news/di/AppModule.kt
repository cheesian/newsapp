package com.example.news.di

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
Created by ian
 */
@Module
class AppModule (private val application: Application) {

    @Provides
    @Singleton
    fun provideApplication() = application

}