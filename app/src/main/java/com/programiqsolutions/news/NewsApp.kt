package com.programiqsolutions.news

import android.app.Application
import com.programiqsolutions.news.di.*

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