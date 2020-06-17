package com.example.news

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.news.data.Constants.PREFERENCE_FILE
import com.example.news.di.*

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