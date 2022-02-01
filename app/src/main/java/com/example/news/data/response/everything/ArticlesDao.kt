package com.example.news.data.response.everything

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*


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

    @Query("SELECT * from sourceIDs")
    fun getSources(): LiveData<List<SourceResponseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSource(source: SourceResponseEntity)

    @Delete
    fun deleteSource(source: SourceResponseEntity)

    @Query("DELETE from sourceIDs")
    fun deleteAllSources()
}