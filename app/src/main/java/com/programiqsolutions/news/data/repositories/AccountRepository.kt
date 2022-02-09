package com.programiqsolutions.news.data.repositories

import androidx.lifecycle.LiveData
import com.programiqsolutions.news.data.daos.AccountDao
import com.programiqsolutions.news.data.request.user.UserEntity
import com.programiqsolutions.news.data.request.signIn.SignInApiService
import com.programiqsolutions.news.data.request.signUp.SignUpApiService
import com.programiqsolutions.news.data.request.user.UserApiService
import com.programiqsolutions.news.data.request.signIn.SignInRequest
import com.programiqsolutions.news.data.request.signUp.SignUpRequest
import com.programiqsolutions.news.data.response.signIn.GetUserResponse
import com.programiqsolutions.news.data.response.signIn.SignInResponse
import com.programiqsolutions.news.data.response.signUp.SignUpResponse
import io.reactivex.Observable
import javax.inject.Inject


/**
Created by ian
 */

class AccountRepository @Inject constructor(
    var signInApiService: SignInApiService,
    var signUpApiService: SignUpApiService,
    var userApiService: UserApiService,
    var accountDao: AccountDao
){
    fun signUp(data: SignUpRequest): Observable<SignUpResponse> {
        return signUpApiService.signUp(data)
    }

    fun signIn(data: SignInRequest): Observable<SignInResponse> {
        return signInApiService.signIn(data)
    }

    fun getUser(): Observable<GetUserResponse> {
        return userApiService.getUser()
    }

    fun insertUser(user: UserEntity) {
        accountDao.insertUser(user)
    }

    fun deleteAllAccounts() {
        accountDao.deleteAllAccounts()
    }

    fun deleteUser(user: UserEntity) {
        accountDao.deleteUser(user)
    }

    fun getUsers(): List<UserEntity> {
        return accountDao.getUsers()
    }

    fun getUsersLiveData(): LiveData<List<UserEntity>> {
        return accountDao.getUsersLiveData()
    }
}