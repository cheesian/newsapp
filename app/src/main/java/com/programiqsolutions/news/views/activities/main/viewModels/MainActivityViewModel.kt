package com.programiqsolutions.news.views.activities.main.viewModels

import androidx.lifecycle.ViewModel
import com.programiqsolutions.news.data.request.user.UserEntity
import com.programiqsolutions.news.data.repositories.AccountRepository


/**
Created by ian
 */

class MainActivityViewModel(
    var accountRepository: AccountRepository
) : ViewModel() {

    fun deleteAccounts() {
        accountRepository.deleteAllAccounts()
    }

    fun getUserList(): List<UserEntity> {
        return accountRepository.getUsers()
    }

}