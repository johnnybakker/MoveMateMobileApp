package nl.johnny.movemate.api

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import nl.johnny.movemate.BuildConfig
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL


class ApiWorker(context: Context, params: WorkerParameters)
    : Worker(context, params) {

    enum class RequestMethod {
        GET,
        POST
    }

    companion object {
        const val TAG = "API_WORKER"
        const val INPUT_PATH: String = "${TAG}_INPUT_PATH"
        const val INPUT_METHOD: String = "${TAG}_INPUT_METHOD"
        const val INPUT_TOKEN: String = "${TAG}_INPUT_TOKEN"
        const val INPUT_PAYLOAD: String = "${TAG}_INPUT_PAYLOAD"
        const val OUTPUT_RESULT: String = "${TAG}_OUTPUT_RESULT"
    }

    override fun doWork(): Result {

        // Requested path
        val path = inputData.getString(INPUT_PATH) ?: ""
        val url = URL("${BuildConfig.API_URL}${path}")
        val token = inputData.getString(INPUT_TOKEN)

        // Create a http or https connection with the API url
        val connection: HttpURLConnection = try {
            url.openConnection() as HttpURLConnection
        } catch (e: IOException) {
            Log.e(TAG, "$e.message?")
            return Result.failure()
        }

        val method = RequestMethod.valueOf(inputData.getString(INPUT_METHOD) ?: "GET")

        // Configure the connection
        connection.requestMethod = method.toString()
        connection.connectTimeout = 2000 // Set timeout to 2 seconds
        connection.readTimeout = 2000 // Set read timeout to 2 seconds
        connection.setRequestProperty("Accept-Charset", "utf-8")
        connection.setRequestProperty("Content-Type", "application/json;charset=utf-8")

        // Set token if provided
        if(!token.isNullOrEmpty()) {
            Log.d(TAG, "Setting bearer: $token")
            connection.setRequestProperty("Authorization", "Bearer $token")
        }

        // Connect with the API
        try {
            connection.connect()
        } catch (e: IOException) {
            Log.e(TAG, "$e.message?")
            return Result.failure()
        } catch (e: SocketTimeoutException) {
            Log.e(TAG, "$e.message?") // Connection timeout
            return Result.failure()
        }

        if(method != RequestMethod.GET) {
            connection.outputStream.use { stream ->
                inputData.getString(INPUT_PAYLOAD)?.let {
                    stream.write(it.toByteArray(Charsets.UTF_8))
                }
                stream.flush()
            }
        }

        Log.d(TAG, "Response code: ${connection.responseCode}")

        // Acquire the input stream from the connection
        val inputStream: InputStream =  try {
            connection.inputStream
        } catch (e: IOException) {
            return Result.failure()
        }

        // Construct a buffered reader
        val reader = BufferedReader(InputStreamReader(inputStream))

        // Read result of http request line by line until there are no lines left
        var result = ""
        for(line: String in reader.lines()){
            result += line
        }

        // Close the stream so that any further reads are blocked
        reader.close()
        inputStream.close()

        // Return the result as workData
        val resultData = workDataOf(OUTPUT_RESULT to result)
        return Result.success(resultData)
    }

}