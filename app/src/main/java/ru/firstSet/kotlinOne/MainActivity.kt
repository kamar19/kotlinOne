package ru.firstSet.kotlinOne

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import org.koin.core.component.KoinApiExtension
import ru.firstSet.kotlinOne.movieList.FragmentMoviesList
import ru.firstSet.kotlinOne.worker.PeriodicWorker
import ru.firstSet.kotlinOne.utils.*
import java.util.concurrent.TimeUnit

@KoinApiExtension
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startPeriodicWork()
//        stopPeriodicWork()
        if (savedInstanceState == null) {
            callFragmentMoviesList()
        } else supportFragmentManager.findFragmentByTag(FRAGMENT_TAG_MOVIES_LIST)
    }

    fun callFragmentMoviesList() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutContainer, FragmentMoviesList(), FRAGMENT_TAG_MOVIES_LIST)
            .commit()
    }

    fun startPeriodicWork() {
        Log.v("startPeriodicWork():", getCurrentDateTimeString())
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
                .setConstraints(myConstraints)
                .build()
        workManager.enqueueUniquePeriodicWork(
            UNIQUE_WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest
        )
    }

    fun stopPeriodicWork() {
        val workManager = WorkManager.getInstance()
        workManager.cancelUniqueWork(UNIQUE_WORK_NAME)
        Log.v("stopPeriodicWork()", getCurrentDateTimeString())
    }

    companion object {
        const val FRAGMENT_TAG_MOVIES_LIST = "MoviesList"
        const val FRAGMENT_TAG_MOVIES_DETAILS = "MoviesDetails"
        val UNIQUE_WORK_NAME = "MoviePeriodicJob"
        val PERIODIC_SERVISE_TIME_DIRATION: Long = 15
        val PERIODIC_SERVISE_TIME_UNIT = TimeUnit.MINUTES

    }
}
