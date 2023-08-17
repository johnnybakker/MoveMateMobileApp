package nl.johnny.movemate.api.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.work.WorkManager
import nl.johnny.movemate.api.ApiRequest
import nl.johnny.movemate.api.ApiService
import nl.johnny.movemate.repositories.IUserRepository
import org.json.JSONObject

class UserRepository<T>(owner: T, service: ApiService) : ApiRepository<T>(owner, service), IUserRepository
        where T : LifecycleOwner, T : Context {

    override fun isLoggedIn(): Boolean = service.hasToken()

    override fun logOut() {
        service.setToken(null)
    }

    override fun logIn(email: String, password: String, onSuccess: () -> Unit) {

        // Prevent another login after login
        if (isLoggedIn()) return onSuccess()

        post(
            path = "/user/login",
            data = JSONObject(
                mapOf(
                    "email" to email,
                    "password" to password
                )
            ),
            onSuccess = {
                Log.d("LOGIN_REPO","RECEIVED: $it")
                service.setToken(it)
                onSuccess()
            },
            onFailure = {
                service.setToken(null)
            }
        )
    }

    override fun signUp(username: String, email: String, password: String, onSuccess: () -> Unit) {
        post(
            path = "/user/signup",
            data = JSONObject(
                mapOf(
                    "email" to email,
                    "password" to password,
                    "username" to username
                )
            ),
            onSuccess = {
                onSuccess()
            },
            onFailure = {

            }
        )
    }

    override fun getAll(cb: (String) -> Unit) {
        get(path = "/user", onSuccess = cb, onFailure = cb)
    }
}