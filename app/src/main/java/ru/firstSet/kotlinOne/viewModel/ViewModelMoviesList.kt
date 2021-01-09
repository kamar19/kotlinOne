package ru.firstSet.kotlinOne.viewModel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.firstSet.kotlinOne.Data.Movie
import ru.firstSet.kotlinOne.Data.RetrofitMovie
import ru.firstSet.kotlinOne.Data.SeachMovie
import ru.firstSet.kotlinOne.View.MainActivity

object ViewModelMoviesList : ViewModel() {
    private var scope = viewModelScope
    private val mutableState = MutableLiveData<ViewModelListState>(ViewModelListState.Loading)
    val stateLiveData: LiveData<ViewModelListState> get() = mutableState
    var retrofitMovie: RetrofitMovie = MainActivity.retrofitMovie
    fun loadMoviewList(seachMovie: SeachMovie): List<Movie> {
        var newMoviesList: List<Movie> = listOf()
        scope.launch {
            newMoviesList = retrofitMovie.loadMovies(seachMovie.seachMovie)
            if (newMoviesList.isEmpty()) {
                mutableState.setValue(ViewModelListState.Error("Size error"))
            } else {
                mutableState.setValue(ViewModelListState.Success(newMoviesList))
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
