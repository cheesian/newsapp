package com.example.news.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import java.net.UnknownHostException

/**
Created by ian
 */

object Notify {

    fun toast (context: Context, message:String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun snackBar(view: View, message: String, actionMessage: String = "Undo", function:View.OnClickListener ) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction(actionMessage, function)
            .show()
    }

    fun log(tag: String = "NewsLog", message: String) {
        Log.d(tag, message)
    }

    fun setErrorMessage(error: Throwable, errorMessageVariable: MutableLiveData<String>) {
        log(message = error.message.toString())
        if (error is UnknownHostException) {
            errorMessageVariable.value = "Check your connection and try again"
        } else {
            errorMessageVariable.value = "Something went wrong. Please try again"
        }
    }

}