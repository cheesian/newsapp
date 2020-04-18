package com.example.news.utils

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtil {
    fun getYMD(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
    }
}