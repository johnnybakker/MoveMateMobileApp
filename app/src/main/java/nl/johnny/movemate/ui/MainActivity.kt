package nl.johnny.movemate.ui

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import nl.johnny.movemate.R
import nl.johnny.movemate.api.models.User
import nl.johnny.movemate.api.repositories.SessionRepository
import nl.johnny.movemate.repositories.IRepositoryBinder
import nl.johnny.movemate.ui.theme.MoveMateTheme
import nl.johnny.movemate.ui.components.TextField

class MainActivity : BaseActivity() {

    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun showNotification() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(applicationContext, getString(R.string.default_notification_channel_id))
            .setContentText("This is som content text")
            .setContentTitle("Hello, world!")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        notificationManager.notify(1, notification)
    }

    override fun onRepositoryBinder(binder: IRepositoryBinder) {

        val userRepository = binder.getUserRepository(this)
        val sessionRepository = binder.getSessionRepository(this)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) return@OnCompleteListener
            sessionRepository.setFirebaseToken(task.result)
        })

        setContent {
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
            }

            val users = remember { mutableStateListOf<User>() }
            var searchUsername by remember { mutableStateOf("") }

            MoveMateTheme {
                Column {
                    TextField(searchUsername, { username ->
                        searchUsername = username
                        if(searchUsername.isNotEmpty()) {
                            userRepository.search(searchUsername) {
                                users.clear()
                                users.addAll(it)
                            }
                        } else {
                            users.clear()
                        }
                    }, placeholder = "Search")

                    Column {
                        users.forEach { user ->
                            Text(user.username)
                        }
                    }

                    Button(onClick = {
                        userRepository.logout()
                        finish()
                    }) {
                        Text(text = "Logout")
                    }
                    Button(onClick = {
                        if (hasNotificationPermission) {
                            showNotification()
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    }) {
                        Text(text = "notification")
                    }
                }
            }
        }
    }

}