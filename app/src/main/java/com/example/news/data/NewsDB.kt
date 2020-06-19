package com.example.news.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.news.data.daos.AccountDao
import com.example.news.data.response.everything.ArticleResponseEntity
import com.example.news.data.daos.ArticlesDao
import com.example.news.data.response.sources.SourceResponseEntity
import com.example.news.data.daos.SourcesDao
import com.example.news.data.daos.TopHeadlinesDao
import com.example.news.data.entities.UserEntity
import com.example.news.data.response.topHeadlines.TopHeadlinesResponseEntity
import com.example.news.utils.SourceResponseEntityConverter


/**
Created by ian
 */

@Database(
    entities = [SourceResponseEntity::class,
        ArticleResponseEntity::class,
        com.example.news.data.response.everything.SourceResponseEntity::class,
        TopHeadlinesResponseEntity::class,
        UserEntity::class
    ],
    version = 4, exportSchema = false
)
@TypeConverters(SourceResponseEntityConverter::class)
abstract class NewsDB : RoomDatabase() {

    abstract fun sourcesDao(): SourcesDao
    abstract fun articlesDao(): ArticlesDao
    abstract fun topHeadlinesDao(): TopHeadlinesDao
    abstract fun accountDao(): AccountDao

}