package com.programiqsolutions.news.services.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.programiqsolutions.news.services.EverythingWorker
import com.programiqsolutions.news.services.TopHeadlinesWorker


/**
Created by ian
 */

class WorkerViewModel (
    val app: Application
): AndroidViewModel(app) {
    private val workManager = WorkManager.getInstance(app)
    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    internal fun deleteExcessiveArticles() {
//        This function will delete excess TopHeadlines and Everything entities from the DB when there is internet connection in an attempt to free storage
        workManager
            .beginWith(OneTimeWorkRequest.Builder(TopHeadlinesWorker::class.java)
                .setConstraints(constraints)
                .build())
            .then(OneTimeWorkRequest.from(EverythingWorker::class.java))
            .enqueue()
    }

    /*@SuppressLint("RestrictedApi")
    fun attachRepositories(): Data {
        val builder = Data.Builder().apply {
            put(TOP_HEADLINES_REPOSITORY_BUNDLE_KEY, topHeadlinesRepository)
            put(EVERYTHING_REPOSITORY_BUNDLE_KEY, everythingRepository)
        }
        return builder.build()
    }*/
}