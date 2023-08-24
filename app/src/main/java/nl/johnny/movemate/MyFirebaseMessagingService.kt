package nl.johnny.movemate

import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import nl.johnny.movemate.api.ApiService
import nl.johnny.movemate.api.repositories.UserRepository
import nl.johnny.movemate.repositories.IRepositoryBinder
import nl.johnny.movemate.repositories.ISessionRepository
import nl.johnny.movemate.repositories.IUserRepository

class MyFirebaseMessagingService : FirebaseMessagingService(), ServiceConnection {

    companion object {
        const val TAG = "FIREBASE_MESSAGING"
    }

    private var userRepository: ISessionRepository? = null
    private var firebaseToken: String? = null;

    override fun onCreate() {
        super.onCreate()

        Intent(this, ApiService::class.java).also { intent ->
            bindService(intent, this, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        firebaseToken = token
        if(userRepository == null) return
        userRepository?.setFirebaseToken(firebaseToken)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

            // Check if data needs to be processed by long running job
            //if (needsToBeScheduled()) {
            // For long-running tasks (10 seconds or more) use WorkManager.
            //scheduleJob()
            // } else {
            // Handle message within 10 seconds
            //handleNow()
        }


        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            showNotification(it.title ?: "", it.body ?: "")
        }
    }

    private fun showNotification(title: String, body: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(applicationContext, getString(R.string.default_notification_channel_id))
            .setContentText(body)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        notificationManager.notify(1, notification)
    }

    override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
        if(binder is IRepositoryBinder) {
            Log.d("FIREBASE", "Firebase connected")
            userRepository = binder.getSessionRepository(this)
            userRepository?.setFirebaseToken(firebaseToken)
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        userRepository = null
    }
}