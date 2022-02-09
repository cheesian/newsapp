package com.programiqsolutions.news.di

import android.app.Application
import androidx.room.Room
import com.programiqsolutions.news.data.NewsDB
import com.programiqsolutions.news.data.daos.AccountDao
import com.programiqsolutions.news.data.daos.ArticlesDao
import com.programiqsolutions.news.data.daos.SourcesDao
import com.programiqsolutions.news.data.daos.TopHeadlinesDao
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton


/**
Created by ian
 */
@Module
class DaoModule (private val application: Application) {

    @Singleton
    @Provides
    fun provideDB(): NewsDB {
        return Room.databaseBuilder(
            application.applicationContext,
            NewsDB::class.java,
            "newsDB"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Reusable
    fun provideArticlesDao (newsDB: NewsDB): ArticlesDao {
        return newsDB.articlesDao()
    }

    @Provides
    @Reusable
    fun provideSourcesDao (newsDB: NewsDB): SourcesDao {
        return newsDB.sourcesDao()
    }

    @Provides
    @Reusable
    fun provideTopHeadlinesDao (newsDB: NewsDB): TopHeadlinesDao {
        return newsDB.topHeadlinesDao()
    }

    @Provides
    @Reusable
    fun provideAccountDao (newsDB: NewsDB): AccountDao {
        return newsDB.accountDao()
    }

}