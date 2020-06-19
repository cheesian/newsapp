package com.example.news.views.activities.main.viewModels

import androidx.lifecycle.ViewModel
import com.example.news.data.entities.UserEntity
import com.example.news.data.repositories.AccountRepository


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