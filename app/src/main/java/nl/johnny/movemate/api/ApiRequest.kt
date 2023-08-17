package nl.johnny.movemate.api

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import org.json.JSONObject

class ApiRequest(
    workManager: WorkManager,
    owner: LifecycleOwner,
    path: String,
    method: String = "GET",
    data: JSONObject = JSONObject(),
    token: String? = null,
    private val onSuccess: (String) -> Unit = {},
    private val onFailure: (String) -> Unit = {}
) {

    private val request: WorkRequest

    init {
        request = OneTimeWorkRequestBuilder<ApiWorker>()
            .setInputData(workDataOf(
                ApiWorker.INPUT_PATH to path,
                ApiWorker.INPUT_METHOD to method,
                ApiWorker.INPUT_PAYLOAD to data.toString(),
                ApiWorker.INPUT_TOKEN to token
            ))
            .setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            )
            .build()

        workManager
            .getWorkInfoByIdLiveData(request.id)
            .observe(owner, this::observer)

        workManager.enqueue(request)
    }

    private fun observer(info: WorkInfo) {
        when(info.state) {
            WorkInfo.State.ENQUEUED -> {}
            WorkInfo.State.RUNNING -> {}
            WorkInfo.State.SUCCEEDED -> {
                Log.i("API_REQUEST", "Request succeeded")
                val result = info.outputData.getString(ApiWorker.OUTPUT_RESULT)
                onSuccess(result.toString())
            }
            WorkInfo.State.FAILED -> {
                Log.i("API_REQUEST", "Request Failed")
                onFailure("")
            }
            WorkInfo.State.BLOCKED -> {}
            WorkInfo.State.CANCELLED -> {}
        }
    }
}