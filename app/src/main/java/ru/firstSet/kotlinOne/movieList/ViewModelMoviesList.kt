package ru.firstSet.kotlinOne.movieList

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.firstSet.kotlinOne.data.*
import ru.firstSet.kotlinOne.repository.RepositoryNet
import ru.firstSet.kotlinOne.repository.RepositoryDB
import ru.firstSet.kotlinOne.utils.getMinutesPassedStart
import java.util.*


@KoinApiExtension
class ViewModelMoviesList(
    private val repositoryNet: RepositoryNet, val repositoryDB: RepositoryDB,
) : ViewModel(), KoinComponent {
    val notifications: MovieNotifications by inject()
    private var scope = viewModelScope
    private val mutableState = MutableLiveData<ViewModelListState>(ViewModelListState.Loading)
    val stateLiveData: LiveData<ViewModelListState> get() = mutableState

    fun loadMovieList(seachMovie: SeachMovie): List<Movie> {
        var movies: List<Movie> = listOf()
        var moviesFromDb: List<Movie> = listOf()
        scope.launch {
            val listFlowRelationMovie: Flow<List<MovieRelation>>
            listFlowRelationMovie =
                repositoryDB.localDataStore.getMoviesSeach(seachMovie.seachMovie)
            listFlowRelationMovie.collect() {
                val listRelationMovie: List<MovieRelation> = it
                moviesFromDb = repositoryDB.convertMovieRelationToMovie(listRelationMovie)
                    .sortedBy { it.ratings }
                if (moviesFromDb.size > 0) {
                    val movieMaxRating: Movie = getMovieWithMaxRating(moviesFromDb)
                    Log.v("MovieWithMaxRating", "${movieMaxRating.title}")
                    // как узнать, что фильмы были из сети, а не первоначальная загрузка при запуске приложения?
                    // выбрать из 4-х веток, один фильм
                    timeLoadFromNet?.let {
                        val countMin: Long = getMinutesPassedStart(it)
                        if (countMin > 10) {
//                            notifications.showNotification(movieMaxRating)
                            Log.v("showNotification>10", "${countMin}")
                        }
                        Log.v("showNotification", "${countMin}")
                    }
                    movies = moviesFromDb
                    mutableState.setValue(ViewModelListState.Success(moviesFromDb))
                }
                Log.v("loadMoviesFromDb", "${moviesFromDb.size}")
            }
        }

        var countDB: Int = 0
        scope.launch(Dispatchers.IO) {
            countDB = repositoryDB.getCountMoviesSeach(seachMovie)
            Log.v("countDB", "${countDB}")

            scope.launch {
                if ((countDB == null) or (countDB <= 0)) {
                    movies = loadAndSaveStartMovieFromNET(seachMovie)
                }
            }
        }
        return movies
    }

    suspend fun loadAndSaveStartMovieFromNET(seachMovie: SeachMovie): List<Movie> {
        var moviesFromNet: List<Movie> = listOf()
        moviesFromNet =
            repositoryNet.loadMoviesFromNET(seachMovie.seachMovie).sortedBy { it.ratings }
        moviesFromNet.let {
            timeLoadFromNet = Calendar.getInstance()
            repositoryDB.saveMoviesToDB(moviesFromNet, seachMovie)
            mutableState.setValue(ViewModelListState.Success(moviesFromNet))
        } ?: mutableState.setValue(ViewModelListState.Error("Size error"))
        return moviesFromNet
    }


    fun getMovieWithMaxRating(movies: List<Movie>): Movie {
        var movie: Movie = movies[0]
        movies.forEach {
            if (it.ratings > movie.ratings) {
                movie = it
            }
        }
        return movie
    }

    companion object {
        var timeLoadFromNet: Calendar? = null
    }


    sealed class ViewModelListState {
        object Loading : ViewModelListState()
        data class Success(val list: List<Movie>) : ViewModelListState()
        data class Error(val error: String) : ViewModelListState()
    }
}
