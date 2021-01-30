package ru.firstSet.kotlinOne.movieList

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.firstSet.kotlinOne.data.Movie
import ru.firstSet.kotlinOne.repository.RepositoryData
import ru.firstSet.kotlinOne.data.SeachMovie

class ViewModelMoviesList(private val repositoryData: RepositoryData) : ViewModel() {
    private var scope = viewModelScope
    private val mutableState = MutableLiveData<ViewModelListState>(ViewModelListState.Loading)
    val stateLiveData: LiveData<ViewModelListState> get() = mutableState

    fun loadMovieList(seachMovie: SeachMovie): List<Movie> {
        var movies: List<Movie> = listOf()
        var moviesFromDb: List<Movie> = listOf()
        scope.launch {
            repositoryData.readMoviesFromDb(seachMovie).also {
                moviesFromDb = it
            }
                if (moviesFromDb.size > 0) {
                    movies = moviesFromDb
                    mutableState.setValue(ViewModelListState.Success(movies))
                    Log.v("get(0)actor.size", "${movies.get(0).actors.size}")

                }



//            repositoryData.readAllGenre()
//            repositoryData.readAllMovieFromDb()



        }
//        scope.launch {
//            movies = repositoryData.loadMoviesFromNET(seachMovie.seachMovie)
////            Log.v("movies.size","${movies.size}")
//
//            movies.let {
//                mutableState.setValue(ViewModelListState.Success(movies))
//                repositoryData.saveMovieToDB(movies, seachMovie)
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
