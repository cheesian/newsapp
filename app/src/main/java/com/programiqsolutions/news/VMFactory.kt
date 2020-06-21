package com.programiqsolutions.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.programiqsolutions.news.data.repositories.AccountRepository
import com.programiqsolutions.news.data.repositories.EverythingRepository
import com.programiqsolutions.news.data.repositories.SourcesRepository
import com.programiqsolutions.news.data.repositories.TopHeadlinesRepository
import com.programiqsolutions.news.views.activities.main.viewModels.MainActivityViewModel
import com.programiqsolutions.news.views.activities.start.viewModels.StartingViewModel
import com.programiqsolutions.news.views.fragments.everything.viewModels.EverythingViewModel
import com.programiqsolutions.news.views.fragments.sources.viewModels.SourcesViewModel
import com.programiqsolutions.news.views.fragments.start.viewModels.SignInViewModel
import com.programiqsolutions.news.views.fragments.start.viewModels.SignUpViewModel
import com.programiqsolutions.news.views.fragments.topHeadlines.viewModels.TopHeadlinesViewModel
import java.lang.IllegalArgumentException
import javax.inject.Inject


/**
Created by ian
 */

class VMFactory @Inject constructor(
    var everythingRepository: EverythingRepository,
    var sourcesRepository: SourcesRepository,
    var topHeadlinesRepository: TopHeadlinesRepository,
    var accountRepository: AccountRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(TopHeadlinesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TopHeadlinesViewModel(
                topHeadlinesRepository
            ) as T
        }

        if (modelClass.isAssignableFrom(SourcesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SourcesViewModel(
                sourcesRepository
            ) as T
        }

        if (modelClass.isAssignableFrom(EverythingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EverythingViewModel(
                everythingRepository
            ) as T
        }

        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignInViewModel(
                accountRepository
            ) as T
        }

        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignUpViewModel(
                accountRepository
            ) as T
        }

        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel(
                accountRepository
            ) as T
        }

        if (modelClass.isAssignableFrom(StartingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StartingViewModel(
                accountRepository
            ) as T
        }

        throw IllegalArgumentException("Unknown class name")

    }

}