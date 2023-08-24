package nl.johnny.movemate.api.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.work.WorkManager
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import nl.johnny.movemate.api.ApiRequest
import nl.johnny.movemate.api.ApiResultType
import nl.johnny.movemate.api.ApiService
import nl.johnny.movemate.api.models.CurrentUser
import nl.johnny.movemate.api.models.User
import nl.johnny.movemate.repositories.IUserRepository
import org.json.JSONObject
import java.lang.Exception

class UserRepository(context: Context, service: ApiService) : ApiRepository(context, service), IUserRepository {

    companion object {
        const val TAG = "USER_REPO"
        val GSON = Gson()
    }

    override fun get(onSuccess: (CurrentUser) -> Unit, onFailure: () -> Unit) {
        Log.d(TAG, "Getting user ${service.userId}, ${service.token}")
        service.currentUser?.let { return onSuccess(it) }


        if(service.userId == -1 || service.token == null)
            return onFailure()

        httpGet("/user/${service.userId}", {
            try {
                onSuccess(GSON.fromJson(it.data, CurrentUser::class.java))
            } catch (e: Exception) {
                onFailure()
            }
        }, onFailure)
    }

    override fun logout() {
        service.currentUser = null
    }

    override fun login(email: String, password: String, onSuccess: (CurrentUser) -> Unit, onFailure: () -> Unit) {
        service.currentUser?.let { return onSuccess(it) }

        httpPost(
            path = "/user/login",
            data = JSONObject(
                mapOf("email" to email, "password" to password)
            ),
            onSuccess = {
                when(it.type) {
                    ApiResultType.Success -> {
                        val currentUser = GSON.fromJson(it.data, CurrentUser::class.java)
                        service.currentUser = currentUser
                        onSuccess(currentUser)
                    }
                    ApiResultType.Failed -> {
                        service.currentUser = null
                        onFailure()
                    }
                }
            },
            onFailure = {
                service.currentUser = null
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