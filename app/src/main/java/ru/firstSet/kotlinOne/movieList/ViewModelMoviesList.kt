package ru.firstSet.kotlinOne.movieList

import android.util.Log
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
        var movies: MutableList<Movie> = mutableListOf()
        var moviesFromDb: List<Movie> = listOf()
        var moviesFromNet: List<Movie> = listOf()
        scope.launch {
                moviesFromDb =repositoryDB.readMoviesFromDb(seachMovie)
                if (moviesFromDb.size > 0) {
                    movies.addAll(moviesFromDb.sortedBy { it.ratings })
                    mutableState.setValue(ViewModelListState.Success(movies))
                }
        }
//        scope.launch {
//            moviesFromNet = repositoryNet.loadMoviesFromNET(seachMovie.seachMovie)
//            moviesFromNet.let {
//                movies.clear()
//                movies.addAll(moviesFromNet.sortedBy { it.ratings })
//                mutableState.setValue(ViewModelListState.Success(movies))
//                repositoryDB.saveMoviesToDB(movies, seachMovie)
//            } ?: mutableState.setValue(ViewModelListState.Error("Size error"))
//        }
        return movies
    }

    sealed class ViewModelListState {
        object Loading : ViewModelListState()
        data class Success(val list: List<Movie>) : ViewModelListState()
        data class Error(val error: String) : ViewModelListState()
    }
}
