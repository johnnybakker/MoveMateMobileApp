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
import androidx.lifecycle.Observer
import com.google.android.gms.location.LocationServices
import com.google.gson.JsonObject
import nl.johnny.movemate.api.models.Workout
import nl.johnny.movemate.ui.MainActivity
import nl.johnny.movemate.utils.TimeUtil
import java.lang.Exception

class WorkoutService : Service(), Observer<Workout?> {

    companion object {
        const val TAG = "WORKOUT_SERVICE"
    }

    private val binder = WorkoutServiceBinder()

    var tracker: WorkoutTracker? = null
        private set

    var trackingData = MutableLiveData<WorkoutTrackingData?>().apply { value = null }
        private set

    private lateinit var manager: NotificationManager
    private lateinit var notification: WorkoutNotification



    inner class WorkoutServiceBinder : Binder() {
        fun getService(): WorkoutService = this@WorkoutService
    }

    private lateinit var app: MoveMateApp

    override fun onCreate() {
        super.onCreate()
        app = application as MoveMateApp
        manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notification = WorkoutNotification(this)

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
        if(tracker != null) return

        startForeground(MoveMateApp.WORKOUT_NOTIFICATION, notification.build())

        tracker = WorkoutTracker(this, app)
        tracker?.startObserve(this)
    }

    private fun stopTracking() {
        if(tracker == null)
            return

        tracker?.stopObserve(this)
        tracker = null

        stopForeground(STOP_FOREGROUND_DETACH)
        stopSelf()
    }

    enum class Command constructor(val value: String) {
        START_WORKOUT("START_WORKOUT"),
        STOP_WORKOUT("STOP_WORKOUT");
    }

    override fun onChanged(value: Workout?) {
        tracker?.let {

            notification.update(it.kmPerHour, it.time)
            manager.notify(MoveMateApp.WORKOUT_NOTIFICATION, notification.build())

            val data = WorkoutTrackingData(it.kmPerHour, it.km, it.time)
            trackingData.postValue(data)
        }
    }
}