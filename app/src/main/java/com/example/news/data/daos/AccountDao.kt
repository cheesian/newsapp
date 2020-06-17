package com.example.news.data.daos

import androidx.room.*
import com.example.news.data.entities.UserEntity


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

}