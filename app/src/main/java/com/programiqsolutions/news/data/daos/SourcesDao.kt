package com.programiqsolutions.news.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.programiqsolutions.news.data.response.sources.SourceResponseEntity


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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSourceList(list: List<SourceResponseEntity>)
}