package ru.firstSet.kotlinOne.movieList

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.firstSet.kotlinOne.data.Movie
import ru.firstSet.kotlinOne.data.MovieRelation
import ru.firstSet.kotlinOne.repository.RepositoryNet
import ru.firstSet.kotlinOne.data.SeachMovie
import ru.firstSet.kotlinOne.repository.RepositoryDB

class ViewModelMoviesList(
    private val repositoryNet: RepositoryNet, val repositoryDB: RepositoryDB,
) : ViewModel() {
    private var scope = viewModelScope
    private val mutableState = MutableLiveData<ViewModelListState>(ViewModelListState.Loading)
    val stateLiveData: LiveData<ViewModelListState> get() = mutableState

    fun loadMovieList(seachMovie: SeachMovie): List<Movie> {
        var moviesFromDb: List<Movie> = listOf()
        var moviesFromNet: List<Movie> = listOf()
        scope.launch {
            val listFlowRelationMovie: Flow<List<MovieRelation>>
            listFlowRelationMovie =
                repositoryDB.localDataStore.getMoviesSeach(seachMovie.seachMovie)
            listFlowRelationMovie.collect() {
                val listRelationMovie: List<MovieRelation> = it
                moviesFromDb = repositoryDB.convertMovieRelationToMovie(listRelationMovie)
                    .sortedBy { it.ratings }
                if (moviesFromDb.size > 0) {
                    getMovieWithMaxRating(moviesFromDb)
                    //Нужно создать Notification для лучшего нового фильма
                    mutableState.setValue(ViewModelListState.Success(moviesFromDb))
                }
                Log.v("loadMoviesFromDb", "${moviesFromDb.size}")
            }
        }
        var countDB: Int = 0
        scope.launch(Dispatchers.IO) {
            countDB = repositoryDB.getCountMoviesSeach(seachMovie)
            scope.launch {
                if ((countDB == null) or (countDB <= 0)) {
                    moviesFromNet =
                        repositoryNet.loadMoviesFromNET(seachMovie.seachMovie)
                            .sortedBy { it.ratings }
                    moviesFromNet.let {
                        repositoryDB.saveMoviesToDB(moviesFromNet, seachMovie)
                        mutableState.setValue(ViewModelListState.Success(moviesFromNet))
                    } ?: mutableState.setValue(ViewModelListState.Error("Size error"))
                }
            }
        }
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


    sealed class ViewModelListState {
        object Loading : ViewModelListState()
        data class Success(val list: List<Movie>) : ViewModelListState()
        data class Error(val error: String) : ViewModelListState()
    }
}
