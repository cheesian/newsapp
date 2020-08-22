package com.programiqsolutions.news.services.background.storage

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.programiqsolutions.news.NewsApp
import com.programiqsolutions.news.R
import com.programiqsolutions.news.data.Constants.EVERYTHING_WORKER_FAILURE
import com.programiqsolutions.news.data.Constants.MIN_ARTICLE_COUNT
import com.programiqsolutions.news.data.repositories.EverythingRepository
import com.programiqsolutions.news.utils.Notify.log
import com.programiqsolutions.news.utils.Notify.makeStatusNotification
import com.programiqsolutions.news.utils.Notify.sleepNotification
import javax.inject.Inject


/**
Created by ian
 */

class EverythingWorker(
    context: Context,
    parameters: WorkerParameters
): Worker(context, parameters) {

    @Inject
    lateinit var everythingRepository: EverythingRepository

    init {
        (applicationContext as NewsApp).applicationComponent.inject(this)
    }

    override fun doWork(): Result {
//        makeStatusNotification(applicationContext.getString(R.string.app_name), "Reducing Everything Articles", applicationContext)
//        sleepNotification()
        return try {
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
            if (it.size > MIN_ARTICLE_COUNT) {
                for (headline in it) {
                    if (it.indexOf(headline) >= MIN_ARTICLE_COUNT) repository.deleteArticle(headline)
                }
            }
        }
    }

}