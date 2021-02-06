package ru.firstSet.kotlinOne

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import org.koin.core.component.KoinApiExtension
import ru.firstSet.kotlinOne.movieList.FragmentMoviesList
import ru.firstSet.kotlinOne.worker.OneTimeWorker
import ru.firstSet.kotlinOne.worker.RepositorySP
import java.util.*

@KoinApiExtension
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startOneTimeWork()
//        stopAllWorkers()
        if (savedInstanceState == null) {
            callFragmentMoviesList()
        } else supportFragmentManager.findFragmentByTag(FRAGMENT_TAG_MOVIES_LIST)
    }

    fun callFragmentMoviesList() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutContainer, FragmentMoviesList(), FRAGMENT_TAG_MOVIES_LIST)
            .commit()
    }

    fun startOneTimeWork() {
        val workManager = WorkManager.getInstance()
        val oneTimeWorkRequest: OneTimeWorkRequest =
            OneTimeWorkRequest.Builder(OneTimeWorker::class.java)
                .setInitialDelay(
                    OneTimeWorker.PERIODIC_SERVISE_TIME_DIRATION,
                    OneTimeWorker.PERIODIC_SERVISE_TIME_UNIT
                )
                .addTag(OneTimeWorker.TAG_FOR_ONE_JOB)
                .build()
        val currentDate = RepositorySP.sdf.format(Date())
        Log.v("startOneTimeWork()", " ${currentDate.toString()}")
        workManager.enqueue(oneTimeWorkRequest)
    }

    fun stopAllWorkers() {
        val workManager = WorkManager.getInstance()
        workManager.cancelAllWorkByTag(OneTimeWorker.TAG_FOR_ONE_JOB)
        workManager.cancelAllWorkByTag(OneTimeWorker.TAG_FOR_PERIODIC_JOB)
        val currentDate = RepositorySP.sdf.format(Date())
        Log.v("stopAllWorkers()", " ${currentDate.toString()}")
    }

    companion object {
        const val FRAGMENT_TAG_MOVIES_LIST = "MoviesList"
        const val FRAGMENT_TAG_MOVIES_DETAILS = "MoviesDetails"
    }
}
