package nl.johnny.movemate.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import nl.johnny.movemate.WorkoutService
import nl.johnny.movemate.api.models.Workout
import nl.johnny.movemate.ui.screens.WorkoutScreen
import nl.johnny.movemate.ui.theme.MoveMateTheme
import nl.johnny.movemate.utils.TimeUtil
import java.sql.Time
import java.time.Instant
import java.time.LocalDateTime
import java.util.Calendar
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : MoveMateActivity(), ServiceConnection {
    companion object {
        const val TAG = "MAIN_ACTIVITY"

    }

//    // Declare the launcher at the top of your Activity/Fragment:
//    private val requestPermissionLauncher = registerForActivityResult(
//        ActivityResultContracts.RequestPermission(),
//    ) { isGranted: Boolean ->
//        if (isGranted) {
//            // FCM SDK (and your app) can post notifications.
//        } else {
//            // TODO: Inform user that that your app will not show notifications.
//        }
//    }
//
//    private fun askNotificationPermission() {
//        // This is only necessary for API level >= 33 (TIRAMISU)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
//                PackageManager.PERMISSION_GRANTED
//            ) {
//                // FCM SDK (and your app) can post notifications.
//            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
//                // TODO: display an educational UI explaining to the user the features that will be enabled
//                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
//                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
//                //       If the user selects "No thanks," allow the user to continue without notifications.
//            } else {
//                // Directly ask for the permission
//                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//            }
//        }
//    }

    private var workoutService: WorkoutService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Intent(this, WorkoutService::class.java).also { intent ->
            bindService(intent, this, Context.BIND_AUTO_CREATE)
        }

        setContent {
            MoveMateTheme(menuItems = menuItems) {


            /*
                val context = LocalContext.current

                var hasNotificationPermission by remember {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        mutableStateOf(
                            ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.POST_NOTIFICATIONS
                                ) == PackageManager.PERMISSION_GRANTED
                        )
                    } else {
                        mutableStateOf(true)
                    }
                }

                val permissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = {
                        hasNotificationPermission = it
                        if(!it) {
                            shouldShowRequestPermissionRationale("This is needed!")
                        }
                    }
                )

                if(!hasNotificationPermission) {
                    Log.d("MAIN_ACTIVITY", "No permission")
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }*/
            }
        }
    }

    override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
        if(binder is WorkoutService.WorkoutServiceBinder) {
            val workoutService: WorkoutService = binder.getService()

            setContent {
                MoveMateTheme(menuItems = menuItems) {
                    var workout by remember { mutableStateOf<Workout?>(null) }
                    var time by remember { mutableStateOf("00:00:00") }

                    workoutService.workout.observe(this@MainActivity) { update ->
                        workout = update
                        Log.d(TAG, "Updating workout")
                        workout?.let {
                            val elapsed = TimeUtil.elapsedMillis(it.startDate.time)
                            time = TimeUtil.secondsToTimeString(elapsed/1000)
                        }
                    }

                    WorkoutScreen(time = time, distance = "0KM", started = workout != null) {
                        Intent(applicationContext, workoutService.javaClass).also {
                            it.action = when(workout == null) {
                                true -> WorkoutService.Command.START_WORKOUT.value
                                false -> WorkoutService.Command.STOP_WORKOUT.value
                            }
                            startService(it)
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