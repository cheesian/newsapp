package com.programiqsolutions.news.services.background.storage.viewModel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.programiqsolutions.news.data.Constants.EVERYTHING_REPOSITORY_BUNDLE_KEY
import com.programiqsolutions.news.data.Constants.TOP_HEADLINES_REPOSITORY_BUNDLE_KEY
import com.programiqsolutions.news.data.repositories.EverythingRepository
import com.programiqsolutions.news.data.repositories.TopHeadlinesRepository
import com.programiqsolutions.news.services.background.storage.EverythingWorker
import com.programiqsolutions.news.services.background.storage.TopHeadlinesWorker


/**
Created by ian
 */

class WorkerViewModel (
    val app: Application,
    var topHeadlinesRepository: TopHeadlinesRepository,
    var everythingRepository: EverythingRepository): AndroidViewModel(app) {

    val workManager = WorkManager.getInstance(app)

    internal fun deleteExcessiveArticles() {
//        This function will delete excess TopHeadlines and Everything entities from the DB in an attempt to free storage
        val schedule = workManager
            .beginWith(OneTimeWorkRequest.Builder(TopHeadlinesWorker::class.java)
            .setInputData(attachRepositories())
                .build())
            .then(OneTimeWorkRequest.from(EverythingWorker::class.java))
            .enqueue()
    }

    @SuppressLint("RestrictedApi")
    fun attachRepositories(): Data {
        val builder = Data.Builder().apply {
            put(TOP_HEADLINES_REPOSITORY_BUNDLE_KEY, topHeadlinesRepository)
            put(EVERYTHING_REPOSITORY_BUNDLE_KEY, everythingRepository)
        }
        return builder.build()
    }
}