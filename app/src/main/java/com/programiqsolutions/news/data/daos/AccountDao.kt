package com.programiqsolutions.news.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.programiqsolutions.news.data.entities.UserEntity


/**
Created by ian
 */

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity)

    @Delete
    fun deleteUser(user: UserEntity)

    @Query("DELETE FROM users")
    fun deleteAllAccounts()

    @Query("SELECT * FROM users")
    fun getUsers(): List<UserEntity>

    @Query("SELECT * FROM users")
    fun getUsersLiveData(): LiveData<List<UserEntity>>
}