package nl.johnny.movemate.utils

import java.util.Calendar

class TimeUtil {
    companion object {
        fun secondsToTimeString(s: Long?): String {
            if(s == null) return "00:00:00"
            val hours = s / 60 / 60
            val minutes = s / 60 % 60
            val seconds = s % 60
            val hoursStr = if (hours < 10) "0$hours" else "$hours"
            val minutesStr = if (minutes < 10) "0$minutes" else "$minutes"
            val secondsStr = if (seconds < 10) "0$seconds" else "$seconds"
            return "$hoursStr:$minutesStr:$secondsStr"
        }

        fun elapsedMillis(s: Long?): Long {
            return System.currentTimeMillis() - (s ?: 0)
        }
    }

}