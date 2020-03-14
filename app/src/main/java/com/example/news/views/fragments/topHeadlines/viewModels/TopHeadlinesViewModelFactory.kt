package com.example.news.views.fragments.topHeadlines.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.news.data.repositories.TopHeadlinesRepository
import javax.inject.Inject


/**
Created by ian
 */

class TopHeadlinesViewModelFactory @Inject constructor(private val topHeadlinesRepository: TopHeadlinesRepository): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TopHeadlinesViewModel(
            topHeadlinesRepository
        ) as T
    }

}