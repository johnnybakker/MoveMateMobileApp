package nl.johnny.movemate.api.repositories

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import nl.johnny.movemate.MoveMateApp
import nl.johnny.movemate.api.ApiResultType
import nl.johnny.movemate.api.models.CurrentUser
import nl.johnny.movemate.api.models.User
import nl.johnny.movemate.repositories.IUserRepository
import org.json.JSONObject
import java.lang.Exception

class UserRepository(app: MoveMateApp) : ApiRepository(app), IUserRepository {

    companion object {
        const val TAG = "USER_REPO"
        val GSON = Gson()
    }

    override fun subscribe(id: Int, callback: (Boolean) -> Unit) {
        httpPost(
            path = "/user/${app.userId}/subscribe",
            data = JSONObject(
                mapOf("id" to id)
            ),
            onFailure = { callback(false) },
            onSuccess = { callback(true) }
        )
    }

    override fun unsubscribe(id: Int, callback: (Boolean) -> Unit) {
        httpPost(
            path = "/user/${app.userId}/unsubscribe",
            data = JSONObject(
                mapOf("id" to id)
            ),
            onFailure = { callback(false) },
            onSuccess = { callback(true) }
        )
    }

    override fun getSubscribers(onSuccess: (subscribers: List<Int>)-> Unit, onFailure: () -> Unit) {
        httpGet("/user/${app.userId}/subscribers", {
            try {
                val typeToken = object : TypeToken<List<Int>>() {}.type
                onSuccess(GSON.fromJson(it.data, typeToken))
            } catch (e: Exception) {
                onFailure()
            }
        }, onFailure)
    }

    override fun getSubscriptions(
        onSuccess: (subscriptions: List<Int>) -> Unit,
        onFailure: () -> Unit
    ) {
        httpGet("/user/${app.userId}/subscriptions", {
            try {
                val typeToken = object : TypeToken<List<Int>>() {}.type
                onSuccess(GSON.fromJson(it.data, typeToken))
            } catch (e: Exception) {
                onFailure()
            }
        }, onFailure)
    }

    override fun get(onSuccess: (CurrentUser) -> Unit, onFailure: () -> Unit) {
        app.currentUser?.let { return onSuccess(it) }

        if(app.userId == -1 || app.token == null)
            return onFailure()

        httpGet("/user/${app.userId}", {
            try {
                onSuccess(GSON.fromJson(it.data, CurrentUser::class.java))
            } catch (e: Exception) {
                onFailure()
            }
        }, onFailure)
    }

    override fun logout() {
        app.currentUser = null
    }

    override fun login(email: String, password: String, onSuccess: (CurrentUser) -> Unit, onFailure: () -> Unit) {
        app.currentUser?.let { return onSuccess(it) }

        httpPost(
            path = "/user/login",
            data = JSONObject(
                mapOf("email" to email, "password" to password)
            ),
            onSuccess = {
                when(it.type) {
                    ApiResultType.Success -> {
                        val currentUser = GSON.fromJson(it.data, CurrentUser::class.java)
                        app.currentUser = currentUser
                        onSuccess(currentUser)
                    }
                    ApiResultType.Failed -> {
                        app.currentUser = null
                        onFailure()
                    }
                }
            },
            onFailure = {
                app.currentUser = null
                onFailure()
            }
        )
    }

    override fun signUp(username: String, email: String, password: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        httpPost(
            path = "/user/signup",
            data = JSONObject(
                mapOf(
                    "email" to email,
                    "password" to password,
                    "username" to username
                )
            ),
            onSuccess = {
                when(it.type) {
                    ApiResultType.Success -> onSuccess()
                    ApiResultType.Failed -> onFailure()
                }
            },
            onFailure = onFailure
        )
    }

    override fun getAll(cb: (List<User>) -> Unit) {
        httpGet(path = "/user",             onSuccess = {
            when(it.type) {
                ApiResultType.Success -> {
                    val typeToken = object : TypeToken<List<User>>() {}.type
                    cb( try { GSON.fromJson(it.data, typeToken) } catch (e: JsonSyntaxException) { listOf() })
                }
                ApiResultType.Failed -> {
                    cb(listOf())
                }
            }
        },
        onFailure = {
            cb(listOf())
        })
    }

    override fun search(value: String, cb: (List<User>) -> Unit) {
        httpGet(
            path = "/user/search?username=$value",
            onSuccess = {
                when(it.type) {
                    ApiResultType.Success -> {
                        val typeToken = object : TypeToken<List<User>>() {}.type
                        cb( try { GSON.fromJson(it.data, typeToken) } catch (e: JsonSyntaxException) { listOf() })
                    }
                    ApiResultType.Failed -> {
                        cb(listOf())
                    }
                }
            },
            onFailure = {
                cb(listOf())
            }
        )
    }
}