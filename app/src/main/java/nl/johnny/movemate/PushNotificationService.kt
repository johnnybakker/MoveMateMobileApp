package nl.johnny.movemate

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService : FirebaseMessagingService() {

    companion object {
        const val TAG = "FIREBASE_MESSAGING"
    }

    private lateinit var app: MoveMateApp

    override fun onCreate() {
        super.onCreate()
        app = application as MoveMateApp
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        app.firebaseToken = token
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let {
            showNotification(it.title ?: "", it.body ?: "")
        }
    }

    private fun showNotification(title: String, body: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(applicationContext, getString(R.string.default_notification_channel_id))
            .setContentText(body)
            .setContentTitle(title)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .build()

        notificationManager.notify(1, notification)
    }
}