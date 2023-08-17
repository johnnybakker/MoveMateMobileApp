package nl.johnny.movemate

import android.app.Application
import android.content.Intent
import android.content.res.Configuration
import android.util.Log

class MoveMateApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Log.i("APPLICATION_MOVEMATE","Create application")
    }

    override fun onTerminate() {
        super.onTerminate()

        Log.i("APPLICATION_MOVEMATE","Terminate application")
    }

    override fun onConfigurationChanged (newConfig : Configuration) {
        super.onConfigurationChanged(newConfig)

        Log.i("APPLICATION_MOVEMATE","Config changed")
    }

    override fun onLowMemory() {
        super.onLowMemory()

        Log.i("APPLICATION_MOVEMATE","Low memory")
    }
}