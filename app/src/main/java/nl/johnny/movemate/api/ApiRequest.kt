package nl.johnny.movemate.api

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import org.json.JSONException
import org.json.JSONObject

class ApiRequest(
    workManager: WorkManager,
    path: String,
    method: String = "GET",
    data: JSONObject? = null,
    token: String? = null,
    private val onSuccess: (ApiResult) -> Unit = {},
    private val onFailure: () -> Unit = {}
) {

    private val request: WorkRequest
    private val liveData: LiveData<WorkInfo>

    init {
        request = OneTimeWorkRequestBuilder<ApiWorker>()
            .setInputData(workDataOf(
                ApiWorker.INPUT_PATH to path,
                ApiWorker.INPUT_METHOD to method,
                ApiWorker.INPUT_TOKEN to token,
                ApiWorker.INPUT_PAYLOAD to data?.toString()
            ))
            .setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            )
            .build()

        liveData = workManager.getWorkInfoByIdLiveData(request.id)
        liveData.observeForever(this::observer)

        workManager.enqueue(request)
    }

    private fun observer(info: WorkInfo) {
        when(info.state) {
            WorkInfo.State.ENQUEUED -> {}
            WorkInfo.State.RUNNING -> {}
            WorkInfo.State.SUCCEEDED -> {
                liveData.removeObserver(this::observer)
                val result = info.outputData.getString(ApiWorker.OUTPUT_RESULT)
                val apiResult = ApiResult.parse(result) ?: return onFailure()
                onSuccess(apiResult)
            }
            else -> {
                liveData.removeObserver(this::observer)
                onFailure()
            }
        }
    }
}