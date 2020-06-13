package com.example.news.di

import android.app.Application
import com.example.news.views.fragments.everything.Everything
import com.example.news.views.fragments.sources.Sources
import com.example.news.views.fragments.topHeadlines.TopHeadlines
import dagger.Component
import javax.inject.Singleton


/**
Created by ian
 */
@Singleton
@Component (modules = [NewsAPIModule::class, DaoModule::class, AppModule::class, BaseAPIModule::class, ProgramiqAPIModule::class])
interface ApplicationComponent {

    fun inject(app: Application)
    fun inject(fragment: Sources)
    fun inject(fragment: TopHeadlines)
    fun inject(fragment: Everything)

}