package nl.johnny.movemate.api

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import nl.johnny.movemate.api.repositories.UserRepository
import nl.johnny.movemate.repositories.IRepositoryBinder
import nl.johnny.movemate.repositories.IUserRepository


class ApiService : Service() {

    private val binder = RepositoryBinder()
    private lateinit var _preferences: SharedPreferences
    private var _token: String? = null

    fun hasToken(): Boolean = !_token.isNullOrEmpty()

    fun getToken(): String? = _token

    fun setToken(token: String?) {
        _token = token

        Log.d("API_SERVICE", "Setting token: $_token")

        with(_preferences.edit()) {
            putString("API_TOKEN", token)
            apply()
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()

        _preferences = getSharedPreferences("REPOSITORY_PREFERENCES", Context.MODE_PRIVATE)
        _token = _preferences.getString("API_TOKEN", null)
        Log.d("API_SERVICE", "Token: $_token")
    }

    override fun onDestroy() {
        super.onDestroy()
        _token = null
    }

    inner class RepositoryBinder : Binder(), IRepositoryBinder {
        override fun <T> getUserRepository(owner: T): IUserRepository
        where T : LifecycleOwner, T : Context {
            return UserRepository(
                owner = owner,
                service = this@ApiService
            )
        }
    }
}