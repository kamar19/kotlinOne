package ru.firstSet.kotlinOne.viewModel

import android.content.Context
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.firstSet.kotlinOne.Data.Movie
import ru.firstSet.kotlinOne.loadMovies

class ViewModelMoviesList : ViewModel() {
    private var scope = viewModelScope
    private val mutableState = MutableLiveData<ViewModelListState>(ViewModelListState.Loading)
    val stateLiveData: LiveData<ViewModelListState> get() = mutableState
    fun loadMoviewList(context: Context) {
        scope.launch {
            val newMoviesList = loadMovies(context)
            if (newMoviesList.isEmpty()) { mutableState.setValue(ViewModelListState.Error("Size error"))}
            else
            { mutableState.setValue(ViewModelListState.Success(newMoviesList))}
        }
    }

    sealed class ViewModelListState {
        object Loading : ViewModelListState()
        data class Success(val list: List<Movie>) : ViewModelListState()
        data class Error(val error: String) : ViewModelListState()
    }
}
