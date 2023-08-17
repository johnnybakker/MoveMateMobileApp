package nl.johnny.movemate.api.repositories;

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.work.WorkManager
import nl.johnny.movemate.api.ApiRequest
import nl.johnny.movemate.api.ApiService
import org.json.JSONObject

abstract class ApiRepository<T>(private val owner: T, protected val service: ApiService)
        where T : LifecycleOwner, T : Context {

    private val workManager = WorkManager.getInstance(owner.applicationContext)

    protected fun get(
        path: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) : ApiRequest {
        return ApiRequest(
            workManager = workManager,
            owner = owner,
            path = path,
            method = "GET",
            token = service.getToken(),
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    protected fun post(
        path: String,
        data: JSONObject,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ): ApiRequest {
        return ApiRequest(
            workManager = workManager,
            owner = owner,
            path = path,
            method = "POST",
            data = data,
            token = service.getToken(),
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

}
