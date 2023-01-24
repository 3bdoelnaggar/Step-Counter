package com.elnaggar.stepcounter

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

const val STEP_COUNTING_CHANNEL_ID: String = "step.counting.channel"

class App : Application() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        val channel = NotificationChannel(
            STEP_COUNTING_CHANNEL_ID,
            "Step counting channel",
            NotificationManager.IMPORTANCE_HIGH
        )

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)


    }
}