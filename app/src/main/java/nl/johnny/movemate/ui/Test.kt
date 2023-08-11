package nl.johnny.movemate.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import nl.johnny.movemate.ui.theme.MoveMateTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import nl.johnny.movemate.ApiWorker

class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val workManager = WorkManager.getInstance(applicationContext)

        setContent {
            MoveMateTheme {

                var status by remember { mutableStateOf("") }

                Column {
                    Button(onClick = {

                        val apiRequest = OneTimeWorkRequestBuilder<ApiWorker>()
                            .setInputData(
                                workDataOf(
                                    ApiWorker.INPUT_KEY_PATH to "/user"
                                )
                            )
                            .setConstraints(
                                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
                            )
                            .build()

                        workManager.enqueue(apiRequest)

                        workManager
                            .getWorkInfoByIdLiveData(apiRequest.id)
                            .observe(this@TestActivity) { info ->
                                info?.let {
                                    when (it.state) {
                                        WorkInfo.State.SUCCEEDED -> {
                                            status = it.outputData.getString(ApiWorker.OUTPUT_KEY_RESULT).toString()
                                            Log.i("OBSERVER", "SUCCEEDED")
                                        }

                                        WorkInfo.State.FAILED -> {
                                            status = "FAILED"
                                            Log.i("OBSERVER", "FAILED")
                                        }

                                        WorkInfo.State.RUNNING -> {
                                            status = "RUNNING"
                                            Log.i("OBSERVER", "RUNNING")
                                        }

                                        else -> {
                                            status = "UNKNOWN"
                                            Log.i("OBSERVER", "UNKNOWN")
                                        }
                                    }
                                }
                            }

                    }) {
                        Text(text = "Enqueue")
                    }

                    Button(onClick = {
                        status = "Cancel"
                    }){
                        Text(text = "Cancel")
                    }

                    Text(text = status)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoveMateTheme {
        Greeting("Android")
    }
}