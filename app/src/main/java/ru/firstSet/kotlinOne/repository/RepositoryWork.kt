package ru.firstSet.kotlinOne.repository

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import java.util.concurrent.TimeUnit

class RepositoryWork {
    //    private val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
    val myConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true) //  Устанавливает, должно ли устройство заряжаться для WorkRequestработы.
            .build()



    val periodicWorkRequest =
            PeriodicWorkRequest.Builder ( WorkerService::class.java,1, TimeUnit.MINUTES,1,TimeUnit.MINUTES)
                    .setConstraints(myConstraints)
                    .build()


}