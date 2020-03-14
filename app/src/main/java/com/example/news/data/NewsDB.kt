package com.example.news.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.news.data.response.everything.ArticleResponseEntity
import com.example.news.data.response.everything.ArticlesDao
import com.example.news.data.response.sources.SourceResponseEntity
import com.example.news.data.response.sources.SourcesDao
import com.example.news.utils.SourceResponseEntityConverter


/**
Created by ian
 */

@Database(entities = [SourceResponseEntity::class, ArticleResponseEntity::class, com.example.news.data.response.everything.SourceResponseEntity::class], version = 2, exportSchema = false)
@TypeConverters(SourceResponseEntityConverter::class)
abstract class NewsDB: RoomDatabase(){

    abstract fun sourcesDao(): SourcesDao
    abstract fun articlesDao(): ArticlesDao

}