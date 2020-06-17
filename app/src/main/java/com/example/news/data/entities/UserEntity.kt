package com.example.news.data.entities

import androidx.room.Entity


/**
Created by ian
 */

@Entity(tableName = "users", primaryKeys = ["username"])

data class UserEntity (

    val username: String = "Janet Doe",
    val email: String = "janet@doe.com",
    val programiq_token: String = "Dummy Token"

)