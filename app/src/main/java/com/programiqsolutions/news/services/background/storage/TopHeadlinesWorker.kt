package com.programiqsolutions.news.services.background.storage

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.programiqsolutions.news.NewsApp
import com.programiqsolutions.news.R
import com.programiqsolutions.news.data.Constants.MAX_ARTICLE_COUNT
import com.programiqsolutions.news.data.Constants.TOP_HEADLINES_WORKER_FAILURE
import com.programiqsolutions.news.data.repositories.TopHeadlinesRepository
import com.programiqsolutions.news.utils.Notify.log
import com.programiqsolutions.news.utils.Notify.makeStatusNotification
import com.programiqsolutions.news.utils.Notify.sleepNotification
import javax.inject.Inject


/**
Created by ian
 */

class TopHeadlinesWorker(
    context: Context,
    workerParams: WorkerParameters
): Worker(context, workerParams) {

    @Inject
    lateinit var topHeadlinesRepository: TopHeadlinesRepository

    init {
        (applicationContext as NewsApp).applicationComponent.inject(this)
    }
    override fun doWork(): Result {
        makeStatusNotification(applicationContext.getString(R.string.app_name), "Reducing TopHeadlines Articles", applicationContext)
        sleepNotification()
        return try {
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
            if (it.size > MAX_ARTICLE_COUNT) {
                for (headline in it) {
                    if (it.indexOf(headline) >= MAX_ARTICLE_COUNT) repository.deleteArticle(headline)
                }
            }
        }
    }

}