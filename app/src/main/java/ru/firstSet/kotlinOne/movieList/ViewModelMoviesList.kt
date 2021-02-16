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
        var moviesFromDb: List<Movie>
        scope.launch {
            val listFlowRelationMovie: Flow<List<MovieRelation>>
            listFlowRelationMovie =
                repositoryDB.localDataStore.getMoviesSeach(seachMovie.seachMovie)
            listFlowRelationMovie.collect() {
                val listRelationMovie: List<MovieRelation> = it
                moviesFromDb = repositoryDB.convertMovieRelationToMovie(listRelationMovie)
                    .sortedBy { it.ratings }
                Log.v("moviesFromDb", "${moviesFromDb.size}")
                if (moviesFromDb.size > 0) {
                    if (seachMovie == SeachMovie.MovieNowPlaying) {
                        moviesFromDb.maxByOrNull { it -> it.ratings }?.let { it1 ->
                            notifications.showNotification(
                                it1
                            )
                        }
                    }
                    movies = moviesFromDb
                    mutableState.setValue(ViewModelListState.Success(moviesFromDb))
                }
            }
        }
        var countDB: Int = 0
        scope.launch(Dispatchers.IO) {
            countDB = repositoryDB.getCountMoviesSeach(seachMovie)
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
            repositoryDB.saveMoviesToDB(moviesFromNet, seachMovie)
            mutableState.setValue(ViewModelListState.Success(moviesFromNet))
        } ?: mutableState.setValue(ViewModelListState.Error("Size error"))
        return moviesFromNet
    }

    sealed class ViewModelListState {
        object Loading : ViewModelListState()
        data class Success(val list: List<Movie>) : ViewModelListState()
        data class Error(val error: String) : ViewModelListState()
    }
}
