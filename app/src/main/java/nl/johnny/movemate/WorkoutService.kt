package nl.johnny.movemate

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import nl.johnny.movemate.api.models.Workout
import nl.johnny.movemate.ui.MainActivity
import nl.johnny.movemate.utils.TimeUtil
import java.lang.Exception

class WorkoutService : Service() {

    companion object {
        const val TAG = "WORKOUT_SERVICE"
    }

    private var kmPerHour = 0.0

    private val binder = WorkoutServiceBinder()

    private lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var notificationManager: NotificationManager

    var workout = MutableLiveData<Workout>().apply { value = null }
    private var workoutThread: Thread? = null
   // private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

    inner class WorkoutServiceBinder : Binder() {
        fun getService(): WorkoutService = this@WorkoutService
    }

    private lateinit var app: MoveMateApp

    override fun onCreate() {
        super.onCreate()
        app = application as MoveMateApp

        notificationBuilder = NotificationCompat.Builder(this, getString(R.string.workout_notification_channel_id))
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        Log.d(TAG, "Service created!")
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.d(TAG, "Service binding!")
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when(intent?.action) {
            Command.START_WORKOUT.value -> startTracking()
            Command.STOP_WORKOUT.value -> stopTracking()
        }

        return START_STICKY
    }

    private fun startTracking() {

        if(workout.value != null)
            return

        app.workoutRepository.create("RUNNING") {
            workout.value = it
            startForeground(MoveMateApp.WORKOUT_NOTIFICATION, buildNotification())

            Log.d(TAG, "Create workout thread")
            workoutThread = Thread(this::doWorkout, "WORKOUT_THREAD")
            workoutThread?.start()
        }
    }

    private fun doWorkout()
    {
        Log.d(TAG, "Starting workout thread")
        while(workout.value != null)
        {
            workout.value?.data?.add(JsonObject())
            workout.postValue(workout.value)

            updateNotificationText()
            notificationManager.notify(MoveMateApp.WORKOUT_NOTIFICATION, notificationBuilder.build())

            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                break
            }
        }
    }

    private fun stopTracking() {
        if(workout.value == null)
            return

        stopForeground(STOP_FOREGROUND_REMOVE)

        workout.value = null
        workoutThread?.interrupt()
        workoutThread = null


        stopSelf()
    }

    private fun buildNotification(): Notification {
        val notifyIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val notifyPendingIntent = PendingIntent.getActivity(
            this, 0, notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        notificationBuilder
            .setSmallIcon(R.mipmap.logo_no_spacing)
            .setContentTitle("Tracking workout")
            .setContentIntent(notifyPendingIntent)
            .setSilent(true)
        updateNotificationText()
        return notificationBuilder.build()
    }

    private fun updateNotificationText() {
        workout.value?.let {
            val elapsed = TimeUtil.elapsedMillis(it.startDate.time)
            val time = TimeUtil.secondsToTimeString(elapsed/1000)
            notificationBuilder.setContentText("Speed: ${"%.1f".format(kmPerHour)} KM/H, Duration: $time")
        }
    }

    enum class Command constructor(val value: String) {
        START_WORKOUT("START_WORKOUT"),
        STOP_WORKOUT("STOP_WORKOUT");
    }
}