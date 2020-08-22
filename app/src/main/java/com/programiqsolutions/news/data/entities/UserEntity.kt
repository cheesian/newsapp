package com.programiqsolutions.news.data.entities

import androidx.room.Entity


/**
Created by ian
 */

@Entity(tableName = "users", primaryKeys = ["username"])

data class UserEntity (

    val username: String,
    val email: String,
    val programiq_token: String

)