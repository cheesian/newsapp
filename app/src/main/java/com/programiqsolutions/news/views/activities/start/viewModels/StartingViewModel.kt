package com.programiqsolutions.news.views.activities.start.viewModels

import androidx.lifecycle.ViewModel
import com.programiqsolutions.news.data.repositories.AccountRepository


/**
Created by ian
 */

class StartingViewModel (
    var accountRepository: AccountRepository
): ViewModel() {

    var users = accountRepository.getUsersLiveData()

}