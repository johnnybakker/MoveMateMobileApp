package nl.johnny.movemate.api.repositories;

import androidx.work.WorkManager
import nl.johnny.movemate.MoveMateApp
import nl.johnny.movemate.api.ApiRequest
import nl.johnny.movemate.api.ApiResult
import org.json.JSONObject

abstract class ApiRepository(protected val app: MoveMateApp) {

    protected fun httpGet(
        path: String,
        onSuccess: (ApiResult) -> Unit,
        onFailure: () -> Unit
    ) : ApiRequest {
        return ApiRequest(
            workManager = WorkManager.getInstance(app),
            path = path,
            method = "GET",
            token = app.token,
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
            workManager = WorkManager.getInstance(app),
            path = path,
            method = "POST",
            data = data,
            token = app.token,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

}
