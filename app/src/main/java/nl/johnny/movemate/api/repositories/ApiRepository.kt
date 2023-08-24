package nl.johnny.movemate.api.repositories;

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.work.WorkManager
import com.google.firebase.messaging.FirebaseMessaging
import nl.johnny.movemate.api.ApiRequest
import nl.johnny.movemate.api.ApiResult
import nl.johnny.movemate.api.ApiService
import org.json.JSONObject

abstract class ApiRepository(context: Context, protected val service: ApiService) {

    private val workManager = WorkManager.getInstance(context.applicationContext)
    //private val messaging = FirebaseMessaging.getInstance()

    protected fun httpGet(
        path: String,
        onSuccess: (ApiResult) -> Unit,
        onFailure: () -> Unit
    ) : ApiRequest {
        return ApiRequest(
            workManager = workManager,
            path = path,
            method = "GET",
            token = service.token,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    protected fun httpPost(
        path: String,
        data: JSONObject?,
        onSuccess: (ApiResult) -> Unit,
        onFailure: () -> Unit
    ): ApiRequest {
        return ApiRequest(
            workManager = workManager,
            path = path,
            method = "POST",
            data = data,
            token = service.token,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

}
