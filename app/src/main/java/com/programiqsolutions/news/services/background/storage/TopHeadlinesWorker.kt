package com.programiqsolutions.news.services.background.storage

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.programiqsolutions.news.R
import com.programiqsolutions.news.data.Constants
import com.programiqsolutions.news.data.Constants.TOP_HEADLINES_REPOSITORY_BUNDLE_KEY
import com.programiqsolutions.news.data.Constants.TOP_HEADLINES_WORKER_FAILURE
import com.programiqsolutions.news.data.repositories.EverythingRepository
import com.programiqsolutions.news.data.repositories.TopHeadlinesRepository
import com.programiqsolutions.news.utils.Notify.log
import com.programiqsolutions.news.utils.Notify.makeStatusNotification


/**
Created by ian
 */

class TopHeadlinesWorker(
    context: Context,
    workerParams: WorkerParameters
): Worker(context, workerParams) {

    override fun doWork(): Result {
        makeStatusNotification(applicationContext.getString(R.string.app_name), "Reducing TopHeadlines Articles", applicationContext)
        val map = inputData.keyValueMap
        return try {
            val topHeadlinesRepository = map.getValue(TOP_HEADLINES_REPOSITORY_BUNDLE_KEY) as TopHeadlinesRepository
            reduceTopHeadlines(topHeadlinesRepository)
            Result.success()
        } catch (exception: Exception) {
            log(tag = TOP_HEADLINES_WORKER_FAILURE, message = exception.message.toString())
            Result.failure()
        }
    }

    private fun reduceTopHeadlines(repository: TopHeadlinesRepository) {
        val list = repository.getArticles().value?.sortedByDescending {
            it.publishedAt
        }
        list?.let {
            if (it.size > 100) {
                for (headline in it) {
                    if (it.indexOf(headline) >= 100) repository.deleteArticle(headline)
                }
            }
        }
    }

}