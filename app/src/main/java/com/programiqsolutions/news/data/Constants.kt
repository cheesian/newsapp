package com.programiqsolutions.news.data

object Constants {
//    API Calls
    const val API_KEY = "c1d6f774dc61411fb14f79f3137488fb"
    const val Q = "a"
    const val LANGUAGE = "en"
    const val SORT_BY = "publishedAt"
    const val PAGE_SIZE = 40

    const val PROGRAMIQ_RETROFIT = "programiqRetrofit"
    const val NEWS_RETROFIT = "newsRetrofit"
    const val PROGRAMIQ_TOKEN_OKHTTP = "programiqTokenOkhttp"
    const val PROGRAMIQ_TOKEN_RETROFIT = "programiqTokenRetrofit"
    const val SIMPLE_OKHTTP = "simpleOkhttp"

//    App Constants
    const val PASSWORD_LENGTH = 5
    const val STORAGE_PERMISSIONS_REQUEST_CODE = 34500
    const val PREFERENCE_FILE = "com.example.news.newsPreference"

//    Worker threads
    const val VERBOSE_NOTIFICATION_CHANNEL_NAME = "WorkManager Notifications"
    const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION = "Displays notifications on the notification bar as the thread executes"
    const val CHANNEL_ID = "VERBOSE NOTIFICATION"
    const val NOTIFICATION_ID = 1

//    Tags and Keys
    const val TOP_HEADLINES_REPOSITORY_BUNDLE_KEY = "topHeadlines"
    const val EVERYTHING_REPOSITORY_BUNDLE_KEY = "everything"
    const val TOP_HEADLINES_WORKER_FAILURE = "THWorkerFailure"
    const val EVERYTHING_WORKER_FAILURE = "EWorkerFailure"

}