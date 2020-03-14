package com.example.news.data.response.sources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*


/**
Created by ian
 */
@Dao
interface SourcesDao {

    @Query("SELECT * FROM sources")
    fun getSources(): LiveData<List<SourceResponseEntity>>

    @Query("DELETE FROM sources")
    fun deleteAllSources()

    @Delete
    fun deleteSource(source: SourceResponseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSource(source: SourceResponseEntity)
}