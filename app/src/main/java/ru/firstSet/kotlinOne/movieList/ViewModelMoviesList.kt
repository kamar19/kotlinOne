package ru.firstSet.kotlinOne.movieList

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.firstSet.kotlinOne.data.Movie
import ru.firstSet.kotlinOne.repository.RepositoryNet
import ru.firstSet.kotlinOne.data.SeachMovie
import ru.firstSet.kotlinOne.repository.RepositoryDB
import ru.firstSet.kotlinOne.worker.RepositorySP

class ViewModelMoviesList(
    private val repositoryNet: RepositoryNet, val repositoryDB: RepositoryDB,
    val repositorySP: RepositorySP
) : ViewModel() {

    private var scope = viewModelScope
    private val mutableState = MutableLiveData<ViewModelListState>(ViewModelListState.Loading)
    val stateLiveData: LiveData<ViewModelListState> get() = mutableState

    fun loadMovieList(seachMovie: SeachMovie): List<Movie> {
        val moviesMutable: MutableList<Movie> = mutableListOf()
        var moviesFromDb: List<Movie> = listOf()
        var moviesFromNet: List<Movie> = listOf()
        scope.launch {
            moviesFromDb = repositoryDB.readMoviesFromDb(seachMovie)
            if (moviesFromDb.size > 0) {
                moviesMutable.addAll(moviesFromDb.sortedBy { it.ratings })
                mutableState.setValue(ViewModelListState.Success(moviesMutable))
            } else {
                moviesFromNet = repositoryNet.loadMoviesFromNET(seachMovie.seachMovie)
                moviesFromNet.let {
                    moviesMutable.clear()
                    moviesMutable.addAll(moviesFromNet.sortedBy { it.ratings })
                    mutableState.setValue(ViewModelListState.Success(moviesMutable))
                    repositoryDB.saveMoviesToDB(moviesMutable, seachMovie)
                    repositorySP.saveCurrentDate()
                } ?: mutableState.setValue(ViewModelListState.Error("Size error"))
            }
        }
        return moviesMutable
    }

    sealed class ViewModelListState {
        object Loading : ViewModelListState()
        data class Success(val list: List<Movie>) : ViewModelListState()
        data class Error(val error: String) : ViewModelListState()
    }
}
