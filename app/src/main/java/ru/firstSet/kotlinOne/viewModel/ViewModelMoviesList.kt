package ru.firstSet.kotlinOne.viewModel

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.firstSet.kotlinOne.Data.Movie
import ru.firstSet.kotlinOne.Data.SeachMovie
import ru.firstSet.kotlinOne.MyAplication.Companion.movieRepository

object ViewModelMoviesList : ViewModel() {
    private var scope = viewModelScope
    private val mutableState = MutableLiveData<ViewModelListState>(ViewModelListState.Loading)
    val stateLiveData: LiveData<ViewModelListState> get() = mutableState

    fun loadMoviewList(seachMovie: SeachMovie): List<Movie> {
        var newMoviesList: List<Movie> = listOf()
        var newMoviesEntryList: List<Movie> = listOf()
        scope.launch {
            movieRepository.readMovieFromDb(seachMovie).also {
//                if (it != null) {
                newMoviesEntryList = it
                Log.v("readMovieFromDb", " ${it.size}")
//            }

                if (newMoviesEntryList.size > 0) {
//                Log.v("newMoviesEntryList","$newMoviesEntryList size>0")
                    newMoviesList = newMoviesEntryList
                    mutableState.setValue(ViewModelListState.Success(newMoviesList))
                }
            }
        }

        scope.launch {
            newMoviesList = movieRepository.loadMoviesFromNET(seachMovie.seachMovie)
            if (newMoviesList.isEmpty()) {
                mutableState.setValue(ViewModelListState.Error("Size error"))
            } else {
                mutableState.setValue(ViewModelListState.Success(newMoviesList))
                Log.v("newMoviesList", " setValue")

                movieRepository.saveMovieToDB(newMoviesList,seachMovie)
            }

        }
        return newMoviesList
    }

    sealed class ViewModelListState {
        object Loading : ViewModelListState()
        data class Success(val list: List<Movie>) : ViewModelListState()
        data class Error(val error: String) : ViewModelListState()
    }

}
