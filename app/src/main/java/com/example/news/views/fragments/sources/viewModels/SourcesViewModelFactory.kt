package com.example.news.views.fragments.sources.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.news.data.repositories.SourcesRepository
import javax.inject.Inject


/**
Created by ian
 */

class SourcesViewModelFactory @Inject constructor(private val sourcesRepository: SourcesRepository): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SourcesViewModel(
            sourcesRepository
        ) as T
    }

}