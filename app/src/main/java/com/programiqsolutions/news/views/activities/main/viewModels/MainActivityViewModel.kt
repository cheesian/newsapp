package com.programiqsolutions.news.views.activities.main.viewModels

import androidx.lifecycle.ViewModel
import com.programiqsolutions.news.data.entities.UserEntity
import com.programiqsolutions.news.data.repositories.AccountRepository


/**
Created by ian
 */

class MainActivityViewModel(
    var accountRepository: AccountRepository
) : ViewModel() {

    var users = accountRepository.getUsersLiveData()

    fun deleteAccounts() {
        accountRepository.deleteAllAccounts()
    }

    fun getUserList(): List<UserEntity> {
        return accountRepository.getUsers()
    }

}