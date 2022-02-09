package com.programiqsolutions.news.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import com.programiqsolutions.news.R
import com.programiqsolutions.news.data.Constants.CHANNEL_ID
import com.programiqsolutions.news.data.Constants.NOTIFICATION_FAILURE
import com.programiqsolutions.news.data.Constants.NOTIFICATION_ID
import com.programiqsolutions.news.data.Constants.SLEEP_DELAY_MILLIS
import com.programiqsolutions.news.data.Constants.VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
import com.programiqsolutions.news.data.Constants.VERBOSE_NOTIFICATION_CHANNEL_NAME
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

    fun makeStatusNotification(title: String, message: String, context: Context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            This only works on API > 26
            val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
            val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
            notificationManager?.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_news)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
    }

    fun sleepNotification() {
//        This function is intended for use where the notification is shown on the notification bar
        try {
            Thread.sleep(SLEEP_DELAY_MILLIS, 0)
        } catch (exception: InterruptedException) {
            log(tag = NOTIFICATION_FAILURE, message = exception.message.toString())
        }
    }

}