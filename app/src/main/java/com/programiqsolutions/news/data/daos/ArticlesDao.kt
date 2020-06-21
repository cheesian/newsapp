package com.programiqsolutions.news.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.programiqsolutions.news.data.response.everything.ArticleResponseEntity
import com.programiqsolutions.news.data.response.everything.SourceResponseEntity


/**
Created by ian
 */
@Dao
interface ArticlesDao {

    @Query("SELECT * from articles")
    fun getArticles(): LiveData<List<ArticleResponseEntity>>

    @Query("DELETE from articles")
    fun deleteAllArticles()

    @Delete
    fun deleteArticle(article: ArticleResponseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article: ArticleResponseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticleList(list: List<ArticleResponseEntity>)

    @Query("SELECT * from sourceIDs")
    fun getSources(): LiveData<List<SourceResponseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSource(source: SourceResponseEntity)

    @Delete
    fun deleteSource(source: SourceResponseEntity)

    @Query("DELETE from sourceIDs")
    fun deleteAllSources()
}