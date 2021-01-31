package ru.firstSet.kotlinOne.movieList

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.firstSet.kotlinOne.data.Movie
import ru.firstSet.kotlinOne.repository.RepositoryNet
import ru.firstSet.kotlinOne.data.SeachMovie
import ru.firstSet.kotlinOne.repository.RepositoryDB

class ViewModelMoviesList(private val repositoryNet: RepositoryNet,val repositoryDB: RepositoryDB) : ViewModel() {
    private var scope = viewModelScope
    private val mutableState = MutableLiveData<ViewModelListState>(ViewModelListState.Loading)
    val stateLiveData: LiveData<ViewModelListState> get() = mutableState

    fun loadMovieList(seachMovie: SeachMovie): List<Movie> {
        var movies: List<Movie> = listOf()
        var moviesFromDb: List<Movie> = listOf()
        scope.launch {
            repositoryDB.readMoviesFromDb(seachMovie).also {
                moviesFromDb = it
            }
                if (moviesFromDb.size > 0) {
                    movies = moviesFromDb
                    mutableState.setValue(ViewModelListState.Success(movies))
                }
        }
        scope.launch {
            movies = repositoryNet.loadMoviesFromNET(seachMovie.seachMovie)
            movies.let {
                mutableState.setValue(ViewModelListState.Success(movies))
                repositoryDB.saveMovieToDB(movies, seachMovie)
            } ?: mutableState.setValue(ViewModelListState.Error("Size error"))
        }
        return movies
    }

    sealed class ViewModelListState {
        object Loading : ViewModelListState()
        data class Success(val list: List<Movie>) : ViewModelListState()
        data class Error(val error: String) : ViewModelListState()
    }
}
