package nl.johnny.movemate.ui

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import nl.johnny.movemate.WorkoutService
import nl.johnny.movemate.WorkoutTrackingData
import nl.johnny.movemate.api.models.Workout
import nl.johnny.movemate.ui.screens.WorkoutScreen
import nl.johnny.movemate.ui.theme.MoveMateTheme
import nl.johnny.movemate.utils.TimeUtil
import kotlin.math.floor
import kotlin.math.round

class MainActivity : MoveMateActivity(), ServiceConnection {
    companion object {
        const val TAG = "MAIN_ACTIVITY"

    }

    private var workoutService: WorkoutService? = null

    @Composable
    fun LocationPermissionRequest(permission: String, onPermissionGranted: () -> Unit) {
        val context = LocalContext.current

        val permissionState = rememberUpdatedState(
            checkLocationPermissionState(context, permission)
        )

        if (permissionState.value) {
            onPermissionGranted()
        } else {
            RequestLocationPermission(permission)
        }
    }

    @Composable
    private fun RequestLocationPermission(permission: String) {

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = {}
        )

        permissionLauncher.launch(permission)
    }

    @Composable
    fun checkLocationPermissionState(context: Context, permission: String): Boolean =
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MoveMateTheme(menuItems = menuItems) {


                Intent(this, WorkoutService::class.java).also { intent ->
                    bindService(intent, this, Context.BIND_AUTO_CREATE)
                }

            }
        }
    }

    override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
        if(binder is WorkoutService.WorkoutServiceBinder) {
            val workoutService: WorkoutService = binder.getService()

            setContent {
                MoveMateTheme(menuItems = menuItems) {
                    var trackingData by remember { mutableStateOf<WorkoutTrackingData?>(null) }

                    workoutService.trackingData.observe(this) { trackingData = it }

                    val permissionLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestMultiplePermissions(),
                        onResult = {
                            Intent(applicationContext, workoutService.javaClass).also {
                                it.action = when(trackingData == null) {
                                    true -> WorkoutService.Command.START_WORKOUT.value
                                    false -> WorkoutService.Command.STOP_WORKOUT.value
                                }
                                startService(it)
                            }
                        }
                    )

                    val time = (trackingData?.time ?: 0L) / 1000
                    val distance = floor((trackingData?.km ?: 0.0) * 100) / 100
                    val speed = trackingData?.kmPerHour ?: 0.0

                    WorkoutScreen(time = TimeUtil.secondsToTimeString(time), distance = "$distance KM", "$speed KM/H", started = trackingData != null) {

                        if (trackingData != null) {

                            Intent(applicationContext, workoutService.javaClass).also {
                                it.action =  WorkoutService.Command.STOP_WORKOUT.value
                                startService(it)
                            }
                            trackingData = null

                        } else {
                            permissionLauncher.launch(
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    arrayOf(
                                        Manifest.permission.POST_NOTIFICATIONS,
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    )

                                } else {
                                    arrayOf(
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        setContent {
            MoveMateTheme(menuItems = menuItems) {

            }
        }
    }

}