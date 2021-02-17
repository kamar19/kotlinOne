package ru.firstSet.kotlinOne

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import androidx.work.*
import org.koin.core.component.KoinApiExtension
import ru.firstSet.kotlinOne.movieDetails.FragmentMovieDetails
import ru.firstSet.kotlinOne.movieList.FragmentMoviesList
import ru.firstSet.kotlinOne.worker.PeriodicWorker
import ru.firstSet.kotlinOne.utils.*
import java.util.concurrent.TimeUnit

@KoinApiExtension
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.commitNow {
                replace(R.id.frameLayoutContainer, FragmentMoviesList(), FRAGMENT_TAG_MOVIES_LIST)
            }
            intent?.let(::handleIntent)
        }
        startPeriodicWork()
//        stopPeriodicWork()
    }

    fun openMovieFragment(id: Long) {
        supportFragmentManager.popBackStack(FRAGMENT_TAG_MOVIES_DETAILS, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.commit {
            addToBackStack(FRAGMENT_TAG_MOVIES_DETAILS)
            replace(R.id.frameLayoutContainer, FragmentMovieDetails.newInstance(id))
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            handleIntent(intent)
        }
    }

    private fun handleIntent(intent: Intent) {
        when (intent.action) {
            Intent.ACTION_VIEW -> {
                    val id: Long? = intent.data?.lastPathSegment?.toLongOrNull()
                Log.v("handleIntent()", "$id")
                    if (id != null) {
                        openMovieFragment(id)
                }
            }
        }
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
        val PERIODIC_SERVISE_TIME_DIRATION: Long = 8
        val PERIODIC_SERVISE_TIME_UNIT = TimeUnit.HOURS
    }
}
