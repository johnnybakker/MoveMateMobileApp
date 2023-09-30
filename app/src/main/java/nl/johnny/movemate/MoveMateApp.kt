package nl.johnny.movemate

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import nl.johnny.movemate.api.models.CurrentUser
import nl.johnny.movemate.api.models.User
import nl.johnny.movemate.api.repositories.SessionRepository
import nl.johnny.movemate.api.repositories.UserRepository
import nl.johnny.movemate.api.repositories.WorkoutRepository
import nl.johnny.movemate.repositories.ISessionRepository
import nl.johnny.movemate.repositories.IUserRepository
import nl.johnny.movemate.repositories.IWorkoutRepository
import nl.johnny.movemate.ui.LoginActivity
import nl.johnny.movemate.ui.MainActivity
import nl.johnny.movemate.ui.SearchActivity
import nl.johnny.movemate.ui.components.MenuItem
import nl.johnny.movemate.R
import java.lang.ClassCastException

class MoveMateApp : Application(), OnCompleteListener<String> {

    private lateinit var preferences: SharedPreferences
    lateinit var userRepository: IUserRepository
    lateinit var sessionRepository: ISessionRepository
    lateinit var workoutRepository: IWorkoutRepository

    var firebaseToken: String? = null
        set(value) {
            field = value
            Log.d(TAG, "Token: $firebaseToken")
            if(field != null) sessionRepository.setFirebaseToken(firebaseToken)
        }

    var currentUser: CurrentUser? = null
        set(value) {

            field = value

            with(preferences.edit()) {
                putInt(APP_USER_ID, value?.id ?: -1)
                putString(APP_USER_TOKEN, value?.token)
                apply()
            }

            Log.d(TAG, "Token: $firebaseToken")
            if(field != null) sessionRepository.setFirebaseToken(firebaseToken)
        }

    val userId: Int get() = currentUser?.id ?: try {
        preferences.getInt(APP_USER_ID, -1)
    } catch (e: ClassCastException) {
        Log.e(TAG, "Failed to cast preference id", e)
        -1
    }

    val token: String? get() = currentUser?.token ?: try {
        preferences.getString(APP_USER_TOKEN, null)
    } catch (e: ClassCastException) {
        Log.e(TAG, "Failed to cast preference token", e)
        null
    }

    override fun onCreate() {
        super.onCreate()

        preferences = getSharedPreferences("_", Context.MODE_PRIVATE)

        userRepository = UserRepository(this)
        sessionRepository = SessionRepository(this)
        workoutRepository = WorkoutRepository(this)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val userChannel = NotificationChannel(
                 getString(R.string.default_notification_channel_id),
                "Subscriber notifications",
                NotificationManager.IMPORTANCE_HIGH
            )

            val workoutChannel = NotificationChannel(
                getString(R.string.workout_notification_channel_id),
                "Workout notifications",
                NotificationManager.IMPORTANCE_HIGH
            )

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(userChannel)
            notificationManager.createNotificationChannel(workoutChannel)
        }

        // Get token
        FirebaseMessaging.getInstance().token.addOnCompleteListener(this)
    }

    override fun onComplete(task: Task<String>) {

        if (!task.isSuccessful) {
            Log.w(PushNotificationService.TAG, "Fetching FCM registration token failed", task.exception)
            return
        }

        firebaseToken = task.result
    }

    companion object {
        const val TAG = "APP"
        const val APP_USER_ID = "APP_USER_ID"
        const val APP_USER_TOKEN = "APP_USER_TOKEN"
        const val USER_NOTIFICATION = 1
        const val WORKOUT_NOTIFICATION = 2
    }
}