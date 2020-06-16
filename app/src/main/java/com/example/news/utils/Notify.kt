package com.example.news.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import retrofit2.HttpException
import java.net.UnknownHostException

/**
Created by ian
 */

object Notify {

    fun toast (context: Context, message:String, length: Int = Toast.LENGTH_LONG) {
        Toast.makeText(context, message, length).show()
    }

    // Only set the actionMessage when you want to specify the action
    fun snackBar(view: View, message: String, actionMessage: String = "", function: () -> Unit = {}) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        if (actionMessage.isBlank()) {
            snackBar.setAction("Dismiss") {
                snackBar.dismiss()
            }
        } else {
            snackBar.setAction(actionMessage) { function() }
        }
        snackBar.show()
    }

    fun log(tag: String = "NewsLog", message: String) {
        Log.d(tag, message)
    }

    fun setErrorMessage(error: Throwable, errorMessageVariable: MutableLiveData<String>) {
        log(tag = "ErrorLog", message = error.message.toString())
        errorMessageVariable.value = with (error) {
            when (this) {

                is HttpException -> {
                    val errorMessage: String
                    val body = this.response()!!.errorBody()
                    errorMessage = try {
                        val json = org.json.JSONObject(body!!.string())
                        json.getString("message")
                    } catch (e: Exception) {
                        e.message.toString()
                    }
                    errorMessage
                }

                is UnknownHostException -> "Check your connection and try again"

                else -> "Something went wrong. Please try again"
            }
        }
    }

}