package com.programiqsolutions.news.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.programiqsolutions.news.data.daos.AccountDao
import com.programiqsolutions.news.data.response.everything.ArticleResponseEntity
import com.programiqsolutions.news.data.daos.ArticlesDao
import com.programiqsolutions.news.data.response.sources.SourceResponseEntity
import com.programiqsolutions.news.data.daos.SourcesDao
import com.programiqsolutions.news.data.daos.TopHeadlinesDao
import com.programiqsolutions.news.data.request.user.UserEntity
import com.programiqsolutions.news.data.response.topHeadlines.TopHeadlinesResponseEntity
import com.programiqsolutions.news.utils.SourceResponseEntityConverter


/**
Created by ian
 */

@Database(
    entities = [SourceResponseEntity::class,
        ArticleResponseEntity::class,
        com.programiqsolutions.news.data.response.everything.SourceResponseEntity::class,
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