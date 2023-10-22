package nl.johnny.movemate

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import nl.johnny.movemate.api.models.Workout
import java.util.concurrent.TimeUnit
import kotlin.math.floor

class WorkoutTracker(context: Context, app: MoveMateApp) : LocationCallback() {

    companion object  {
        const val TAG = "WORKOUT_TRACKER"
    }

    private val locationProvider = LocationServices.getFusedLocationProviderClient(context)
    private var workout = MutableLiveData<Workout?>()

    private val locations = mutableListOf<Location>()

    var km = 0.0
        private set

    var time = 0L
        private set

    private var lastValidTime = 0L

    val kmPerHour: Double
        get() {
            return if (lastValidTime > 0 && km > 0.01) {
                floor((km * 1000) / (lastValidTime / 1000) * 3.6 * 10) / 10
            } else {
                0.0
            }
        }

    private var startTime = 0L


    override fun onLocationResult(result: LocationResult) {

        workout.value?.let {
            time = System.currentTimeMillis() - startTime
        }

        result.lastLocation?.let {

            if(locations.size == 0) {
                locations.add(it)
                return workout.postValue(workout.value)
            }

            val distance = locations[locations.size - 1].distanceTo(it)

            if (distance < 5) {
                workout.postValue(workout.value)
                return
            }

            lastValidTime = time

            locations.add(it)

            km += distance / 1000.0
        }

        workout.postValue(workout.value)
    }

    fun startObserve(observer: Observer<Workout?>) {
        workout.observeForever(observer)
    }

    fun stopObserve(observer: Observer<Workout?>) {
        workout.removeObserver(observer)
    }

    private val locationRequest = LocationRequest.Builder(TimeUnit.MILLISECONDS.toMillis(500))
        .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
        .build()

    init {

        workout.value = null

        val accessFineLocation = ActivityCompat
            .checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

        val accessCoarseLocation = ActivityCompat
            .checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        app.workoutRepository.create("RUNNING") {
            workout.value = it
            startTime = System.currentTimeMillis()

            if (accessFineLocation || accessCoarseLocation )
                locationProvider.requestLocationUpdates(locationRequest, this, Looper.getMainLooper())
        }
    }
}