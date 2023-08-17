package nl.johnny.movemate.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import nl.johnny.movemate.api.ApiService
import nl.johnny.movemate.repositories.IRepositoryBinder

abstract class BaseActivity : ComponentActivity(), ServiceConnection {

    abstract fun onRepositoryBinder(binder: IRepositoryBinder)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Text("Connecting")
        }
    }

    override fun onStart() {
        super.onStart()

        // Bind to the user service
        Intent(this, ApiService::class.java).also { intent ->
           bindService(intent, this, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(this)
    }

    override fun onServiceConnected(className: ComponentName, binder: IBinder) {
        if(binder is IRepositoryBinder) {
            onRepositoryBinder(binder)
        }
    }

    override fun onServiceDisconnected(className: ComponentName) {

    }
}