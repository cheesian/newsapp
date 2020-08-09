package com.programiqsolutions.news.services.background.storage

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.programiqsolutions.news.R
import com.programiqsolutions.news.data.Constants.EVERYTHING_REPOSITORY_BUNDLE_KEY
import com.programiqsolutions.news.data.Constants.EVERYTHING_WORKER_FAILURE
import com.programiqsolutions.news.data.repositories.EverythingRepository
import com.programiqsolutions.news.utils.Notify
import com.programiqsolutions.news.utils.Notify.log
import com.programiqsolutions.news.utils.Notify.makeStatusNotification


/**
Created by ian
 */

class EverythingWorker(
    context: Context,
    parameters: WorkerParameters
): Worker(context, parameters) {

    override fun doWork(): Result {
        makeStatusNotification(applicationContext.getString(R.string.app_name), "Reducing Everything Articles", applicationContext)
        val map = inputData.keyValueMap
        return try {
            val everythingRepository = map.getValue(EVERYTHING_REPOSITORY_BUNDLE_KEY) as EverythingRepository
            reduceEverything(everythingRepository)
            Result.success()
        } catch (exception: Exception) {
            log(tag = EVERYTHING_WORKER_FAILURE, message = exception.message.toString())
            Result.failure()
        }
    }

    private fun reduceEverything(repository: EverythingRepository) {
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