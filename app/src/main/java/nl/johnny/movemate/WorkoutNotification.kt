package nl.johnny.movemate

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import nl.johnny.movemate.ui.MainActivity
import nl.johnny.movemate.utils.TimeUtil
import java.util.Date

class WorkoutNotification(context: Context) : NotificationCompat.Builder(context, context.getString(R.string.workout_notification_channel_id)) {

    init {
        val notifyIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val notifyPendingIntent = PendingIntent.getActivity(
            context, 0, notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        setSmallIcon(R.mipmap.logo_no_spacing)
        setContentTitle("Tracking workout")
        setContentIntent(notifyPendingIntent)
        setContentText("Initializing...")
        setSilent(true)
    }

    fun update(kmPerHour: Double, time: Long) {
        val timeStr = TimeUtil.secondsToTimeString(time/1000)
        setContentText("Speed: ${"%.1f".format(kmPerHour)} KM/H, Duration: $timeStr")
    }
}