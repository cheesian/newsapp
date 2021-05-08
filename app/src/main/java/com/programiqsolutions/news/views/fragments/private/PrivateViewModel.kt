package com.programiqsolutions.news.views.fragments.private

import androidx.lifecycle.ViewModel


/**
Created by ian
 */

class PrivateViewModel: ViewModel() {

    fun checkIfLoggedIn (): Boolean = true

    fun checkIfPasswordIsCorrect(): Boolean = true

    fun checkIfHasToken(): Boolean = true

    fun getToken(): String = "Dummy Token"

}