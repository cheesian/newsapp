package com.programiqsolutions.news.di

import android.app.Application
import com.programiqsolutions.news.views.activities.main.MainActivityDrawer
import com.programiqsolutions.news.views.activities.start.StartingActivity
import com.programiqsolutions.news.views.fragments.everything.Everything
import com.programiqsolutions.news.views.fragments.feedback.Feedback
import com.programiqsolutions.news.views.fragments.sources.Sources
import com.programiqsolutions.news.views.fragments.start.SignIn
import com.programiqsolutions.news.views.fragments.start.SignUp
import com.programiqsolutions.news.views.fragments.topHeadlines.TopHeadlines
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
    fun inject(fragment: SignIn)
    fun inject(fragment: SignUp)
    fun inject(activity: MainActivityDrawer)
    fun inject(activity: StartingActivity)
    fun inject(fragment: Feedback)

}