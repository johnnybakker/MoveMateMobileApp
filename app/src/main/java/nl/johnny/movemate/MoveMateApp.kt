package nl.johnny.movemate

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging

class MoveMateApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                 getString(R.string.default_notification_channel_id),
                "Default notification",
                NotificationManager.IMPORTANCE_HIGH
            )

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onTerminate() {
        super.onTerminate()

        Log.i("APPLICATION_MOVEMATE","Terminate application")
    }

    override fun onConfigurationChanged (newConfig : Configuration) {
        super.onConfigurationChanged(newConfig)

        Log.i("APPLICATION_MOVEMATE","Config changed")
    }

    override fun onLowMemory() {
        super.onLowMemory()

        Log.i("APPLICATION_MOVEMATE","Low memory")
    }
}