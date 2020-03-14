package com.example.news.views.fragments.everything.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.news.data.repositories.EverythingRepository
import javax.inject.Inject


/**
Created by ian
 */

class EverythingViewModelFactory @Inject constructor(private val everythingRepository: EverythingRepository): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EverythingViewModel(
            everythingRepository
        ) as T
    }

}