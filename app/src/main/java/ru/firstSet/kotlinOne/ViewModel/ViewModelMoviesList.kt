package ru.firstSet.kotlinOne.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.firstSet.kotlinOne.Data.Movie
import ru.firstSet.kotlinOne.loadMovies

class ViewModelMoviesList(val context: Context) : ViewModel() {
    private var scope = CoroutineScope(Dispatchers.Main)
    private val mutableMoviesList = MutableLiveData<List<Movie>>(emptyList())
    private val mutableState = MutableLiveData<State>(State.LoadingMoviewList)
    private val idLiveData = MutableLiveData<Int>(0)
    val moviesList: LiveData<List<Movie>> get() = mutableMoviesList
    val stateLiveData: LiveData<State> get() = mutableState
    val id: LiveData<Int> get() = idLiveData

    fun loadMoviewList() {
        var newMoviesList: List<Movie> = listOf()
        scope.launch {
            newMoviesList = loadMovies(context)
            val updatedMoviesList = mutableMoviesList.value?.plus(newMoviesList).orEmpty()
            mutableMoviesList.setValue(updatedMoviesList)
            mutableState.setValue(State.ShowedMoviewList)
        }
    }

    fun setId(id: Int) {
        idLiveData.setValue(id)
        mutableState.setValue(State.ShowedMoviewDetails)
    }

    enum class State {
        LoadingMoviewList,
        ShowedMoviewList,
        ShowedMoviewDetails
    }
}