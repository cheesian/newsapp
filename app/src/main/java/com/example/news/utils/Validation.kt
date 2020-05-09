package com.example.news.utils

import android.util.Patterns

object Validation {

    fun checkEmailValidity(email: String): Boolean {
        return with(email.trim()) {
            isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
        }
    }

    fun checkPasswordValidity(password: String, passLength: Int): Boolean {
        return with(password.trim()) {
            isNotBlank() && length >= passLength
        }
    }

    fun hasOnlyLettersAndSpaces(input: String): Boolean {
        return with(input.trim()) {
            isNotBlank() && "[A-Z a-z]+".toRegex().matches(this)
        }
    }
}