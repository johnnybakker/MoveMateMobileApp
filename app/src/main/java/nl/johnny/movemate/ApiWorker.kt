package nl.johnny.movemate

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.delay
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.SocketTimeoutException
import java.net.URI
import java.net.URL
import java.net.URLConnection
import java.time.Duration

class ApiWorker(context: Context, params: WorkerParameters)
    : Worker(context, params) {

    companion object {
        const val TAG = "API_WORKER"
        const val INPUT_KEY_PATH: String = "INPUT_KEY_PATH"
        const val OUTPUT_KEY_RESULT: String = "OUTPUT_KEY_RESULT"
    }

    override fun doWork(): Result {

        // Requested path
        val path = inputData.getString(INPUT_KEY_PATH)
        val url = URL("${BuildConfig.API_URL}${path}")

        // Create a http or https connection with the API url
        val connection: URLConnection = try {
            url.openConnection()
        } catch (e: IOException) {
            Log.e(TAG, "$e.message?")
            return Result.failure()
        }

        // Configure the connection
        connection.connectTimeout = 2000 // Set timeout to 2 seconds
        connection.readTimeout = 2000 // Set read timeout to 2 seconds

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

        // Acquire the input stream from the connection
        val inputStream: InputStream =  try {
            connection.getInputStream()
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
        val resultData = workDataOf(OUTPUT_KEY_RESULT to result)
        return Result.success(resultData)
    }

}