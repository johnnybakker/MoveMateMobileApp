package nl.johnny.movemate

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.google.common.util.concurrent.ListenableFuture

class ApiWorker(context: Context, params: WorkerParameters)
    : ListenableWorker(context, params) {


    override fun startWork(): ListenableFuture<Result> {
        TODO("Not yet implemented")
    }


}