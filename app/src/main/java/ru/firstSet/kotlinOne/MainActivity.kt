package ru.firstSet.kotlinOne

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import org.koin.android.ext.android.get
import ru.firstSet.kotlinOne.movieList.FragmentMoviesList
import ru.firstSet.kotlinOne.repository.RepositoryWork

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repositoryWork: RepositoryWork = get()
        val periodicWork : PeriodicWorkRequest = repositoryWork.periodicWorkRequest
        val workManager = WorkManager.getInstance(applicationContext).enqueue(periodicWork)


        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            callFragmentMoviesList()
        } else supportFragmentManager.findFragmentByTag(FRAGMENT_TAG_MOVIES_LIST)

    }

    companion object {
        const val FRAGMENT_TAG_MOVIES_LIST = "MoviesList"
        const val FRAGMENT_TAG_MOVIES_DETAILS = "MoviesDetails"
    }

    fun callFragmentMoviesList() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutContainer, FragmentMoviesList(), FRAGMENT_TAG_MOVIES_LIST)
            .commit()
    }
}
