package nl.johnny.movemate.api

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import nl.johnny.movemate.api.models.CurrentUser
import nl.johnny.movemate.api.repositories.SessionRepository
import nl.johnny.movemate.api.repositories.UserRepository
import nl.johnny.movemate.repositories.IRepositoryBinder
import nl.johnny.movemate.repositories.ISessionRepository
import nl.johnny.movemate.repositories.IUserRepository


class ApiService : Service() {

    companion object {
        const val TAG = "API_SERVICE"
        private const val API_USER_TOKEN = "API_USER_TOKEN"
        private const val API_USER_ID = "API_USER_ID"
    }

    private val binder = RepositoryBinder()
    private lateinit var _preferences: SharedPreferences

    var firebaseToken: String? = null
    var currentUser: CurrentUser? = null
        set(value) {
            with(_preferences.edit()) {
                Log.d(UserRepository.TAG, "Saving ${value?.id}, ${value?.token}")
                putInt(API_USER_ID, value?.id ?: -1)
                putString(API_USER_TOKEN, value?.token)
                apply()
            }
            field = value
        }

    val userId: Int get() = currentUser?.id ?:  _preferences.getInt(API_USER_ID, -1)
    val token: String? get() = currentUser?.token ?: _preferences.getString(API_USER_TOKEN, null)

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        _preferences = getSharedPreferences("_", Context.MODE_PRIVATE)
    }

    inner class RepositoryBinder : Binder(), IRepositoryBinder {
        override fun getUserRepository(context: Context): IUserRepository {
            return UserRepository(
                context = context,
                service = this@ApiService
            )
        }

        override fun getSessionRepository(context: Context): ISessionRepository {
            return SessionRepository(context, this@ApiService)
        }
    }
}