package ru.firstSet.kotlinOne.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.firstSet.kotlinOne.Data.Movie

class ViewModelMovieDetails (movie: Movie? ){
    private val movieDetailLiveData = MutableLiveData<Movie>(movie)
    private val movieDetailState = MutableLiveData<State>(State.ShowedMoviewDetails)
    val movieDetailStateLiveData: LiveData<State> get() = movieDetailState
    val movieDetailMovie: LiveData<Movie> get() = movieDetailLiveData

    enum class State {
        ShowedMoviewDetails,
        ShowedMoviewList
    }
    fun changeStateMovieDetail(){
        movieDetailState.setValue(State.ShowedMoviewList )
    }

}