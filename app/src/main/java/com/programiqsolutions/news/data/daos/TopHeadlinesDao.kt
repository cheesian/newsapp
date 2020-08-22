package com.programiqsolutions.news.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.programiqsolutions.news.data.response.everything.SourceResponseEntity
import com.programiqsolutions.news.data.response.topHeadlines.TopHeadlinesResponseEntity


/**
Created by ian
 */
@Dao
interface TopHeadlinesDao {
    @Query("SELECT * from topHeadlines")
    fun getArticles(): LiveData<List<TopHeadlinesResponseEntity>>


    @Query("SELECT * from topHeadlines")
    fun getArticleList(): List<TopHeadlinesResponseEntity>

    @Query("DELETE from topHeadlines")
    fun deleteAllArticles()

    @Delete
    fun deleteArticle(article: TopHeadlinesResponseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article: TopHeadlinesResponseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticleList(list: List<TopHeadlinesResponseEntity>)

    @Query("SELECT * from sourceIDs")
    fun getSources(): LiveData<List<SourceResponseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSource(source: SourceResponseEntity)

    @Delete
    fun deleteSource(source: SourceResponseEntity)

    @Query("DELETE from sourceIDs")
    fun deleteAllSources()
}