package ru.firstSet.kotlinOne.repository

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class WorkerService(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        try {
            for (attempt in 0..10) {
                Log.v("WorkerService", "doWork():$attempt")
                Thread.sleep(1000)
            }
            Log.v("end work in ", "work")

            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }
}