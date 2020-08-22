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

//    Feedback Endpoint
    const val FEEDBACK_CONGRATS_OPTION = "I like something about the app"
    const val FEEDBACK_CONGRATS_VALUE = "congratulations"
    const val FEEDBACK_ERROR_OPTION = "I dislike something about the app"
    const val FEEDBACK_ERROR_VALUE = "error"
    const val FEEDBACK_SUGGESTION_OPTION = "I have an improvement suggestion"
    const val FEEDBACK_SUGGESTION_VALUE = "suggestion"
    const val FEEDBACK_DEFAULT_OPTION = "Click & Select the Type of Feedback"

//    App Constants
    const val PASSWORD_LENGTH = 5
    const val STORAGE_PERMISSIONS_REQUEST_CODE = 34500
    const val SLEEP_DELAY_MILLIS: Long = 3000
    const val MIN_ARTICLE_COUNT = 30
    const val PREFERENCE_FILE = "com.example.news.newsPreference"

//    Worker threads
    const val VERBOSE_NOTIFICATION_CHANNEL_NAME = "WorkManager Notifications"
    const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION = "Displays notifications on the notification bar as the thread executes"
    const val CHANNEL_ID = "VERBOSE NOTIFICATION"
    const val NOTIFICATION_ID = 1

//    Tags and Keys
    const val TOP_HEADLINES_WORKER_FAILURE = "THWorkerFailure"
    const val EVERYTHING_WORKER_FAILURE = "EWorkerFailure"
    const val NOTIFICATION_FAILURE = "Notification"

}