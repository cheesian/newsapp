package com.example.news

import android.app.Application
import com.example.news.di.*
import com.example.news.views.fragments.everything.Everything
import com.example.news.views.fragments.sources.Sources
import com.example.news.views.fragments.topHeadlines.TopHeadlines
import dagger.Component
import javax.inject.Singleton


/**
Created by ian
 */

class NewsApp: Application() {

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .appModule(AppModule(this))
            .daoModule(DaoModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent.inject(this)
    }

}