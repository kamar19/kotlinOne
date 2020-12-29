package ru.firstSet.kotlinOne.viewModel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.firstSet.kotlinOne.Data.Movie
import ru.firstSet.kotlinOne.View.FragmentMovieDetails

class ViewModelMovieDetails() : ViewModel() {
    private val movieDetailState =
        MutableLiveData<ViewModelDetailState>(ViewModelDetailState.Loading)
    val movieDetailStateLiveData: LiveData<ViewModelDetailState> get() = movieDetailState

    fun getMovie(bundle: Bundle) {
        val movie: Movie? = bundle.getParcelable<Movie>(FragmentMovieDetails.KEY_PARSE_DATA)
        movie?.let {movieDetailState.setValue(ViewModelDetailState.Success(it))}?:movieDetailState.setValue(
            ViewModelDetailState.Error("Movie not find"))
    }

    sealed class ViewModelDetailState {
        object Loading : ViewModelDetailState()
        data class Success(val movie: Movie) : ViewModelDetailState()
        data class Error(val error: String) : ViewModelDetailState()
    }
}
