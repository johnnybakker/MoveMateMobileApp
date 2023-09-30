package nl.johnny.movemate.utils

import android.location.Location
import kotlin.math.sin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.math.atan2

class GPSUtil {
    companion object {
        /**
         * @param a latitude and longitude pair of point a
         * @param b latitude and longitude pair of point b
         * @return distance between the two points in meters
         */
        fun haversine(a: Location, b: Location): Double {
            // Convert latitude and longitude from degrees to radians
            val lat1Rad = Math.toRadians(a.latitude)
            val lon1Rad = Math.toRadians(a.longitude)
            val lat2Rad = Math.toRadians(b.latitude)
            val lon2Rad = Math.toRadians(b.longitude)

            // Haversine formula
            val dLat = lat2Rad - lat1Rad
            val dLon = lon2Rad - lon1Rad
            val angle = sin(dLat / 2).pow(2) + cos(lat1Rad) * cos(lat2Rad) * sin(dLon / 2).pow(2)
            val c = 2 * atan2(sqrt(angle), sqrt(1 - angle))
            val r = 6371000.0 // Earth's radius in kilometers
            return r * c
        }
    }
}