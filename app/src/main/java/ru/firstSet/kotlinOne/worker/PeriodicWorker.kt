package ru.firstSet.kotlinOne.worker

import android.content.Context
import android.util.Log
import androidx.work.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.firstSet.kotlinOne.data.Movie
import ru.firstSet.kotlinOne.data.SeachMovie
import ru.firstSet.kotlinOne.repository.RepositoryDB
import ru.firstSet.kotlinOne.repository.RepositoryNet
import java.util.*

@KoinApiExtension
class PeriodicWorker(val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params),
    KoinComponent {
    private var repositorySP: RepositorySP = RepositorySP(context)
    val repositoryNet: RepositoryNet by inject()
    val repositoryDB: RepositoryDB by inject()

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                if (repositorySP.compareSavedDate() >= OneTimeWorker.PERIODIC_SERVISE_TIME_DIRATION) {
                    loadNet()
                    repositorySP.saveCurrentDate()
                } else
                    Log.v("Periodic, doWork()", "NOT")
                val currentDate = RepositorySP.sdf.format(Date())
                Log.v("Periodic, doWork()", "${currentDate.toString()}")
                return@withContext Result.success()
            } catch (e: Exception) {
                return@withContext Result.failure()
            }
        }
    }

    suspend fun loadNet() {
        val moviesFromNet: MutableList<Movie> = mutableListOf()
        moviesFromNet.addAll(repositoryNet.loadMoviesFromNET(SeachMovie.MovieNowPlaying.seachMovie))
        moviesFromNet.addAll(repositoryNet.loadMoviesFromNET(SeachMovie.MoviePopular.seachMovie))
        moviesFromNet.addAll(repositoryNet.loadMoviesFromNET(SeachMovie.MovieTopRated.seachMovie))
        moviesFromNet.addAll(repositoryNet.loadMoviesFromNET(SeachMovie.MovieUpComing.seachMovie))
        moviesFromNet.let {
            repositoryDB.saveMoviesToDB(moviesFromNet, SeachMovie.MovieNowPlaying)
        }
        Log.v("ru.firstSet.kotlinOne", "loadNet() end")
    }

    companion object {
    }

}



