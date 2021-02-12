package ru.firstSet.kotlinOne.worker

import android.content.Context
import android.util.Log
import androidx.work.*
import org.koin.core.component.KoinApiExtension
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class OneTimeWorker(val context: Context, params: WorkerParameters) : Worker(context, params) {

    @KoinApiExtension
    override fun doWork(): Result {
        try {
            startPeriodicWork()
            val currentDate = sdf.format(Date())
            Log.v("PeriodicWork, doWork()", "${currentDate.toString()}")
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }

    @KoinApiExtension
    fun startPeriodicWork() {
        val workManager = WorkManager.getInstance()
        val myConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .build()
        val periodicWorkRequest: PeriodicWorkRequest =
            PeriodicWorkRequestBuilder<PeriodicWorker>(
                PERIODIC_SERVISE_TIME_DIRATION,
                PERIODIC_SERVISE_TIME_UNIT
            )
                .addTag(TAG_FOR_PERIODIC_JOB)
                .setConstraints(myConstraints)
                .build()
        workManager.enqueueUniquePeriodicWork(
            UNIQUE_WORK_NAME_FOR_PERIODIC_WORK, ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest
        )
        val currentDate = sdf.format(Date())
        Log.v("startPeriodicWork():", "${currentDate.toString()}")
    }

    companion object {
        val TAG_FOR_ONE_JOB = "OneTimeJob"
        val UNIQUE_WORK_NAME_FOR_ONE_JOB_WORK = "MovieOneTimeJob"
        val TAG_FOR_PERIODIC_JOB = "PeriodicJob"
        val UNIQUE_WORK_NAME_FOR_PERIODIC_WORK = "MoviePeriodicJob"
        val PERIODIC_SERVISE_TIME_DIRATION: Long = 15
        val PERIODIC_SERVISE_TIME_UNIT = TimeUnit.MINUTES
         val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.ENGLISH)
    }
}