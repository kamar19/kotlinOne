package ru.firstSet.kotlinOne.viewModel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.firstSet.kotlinOne.Data.Actor
import ru.firstSet.kotlinOne.Data.Movie
import ru.firstSet.kotlinOne.Data.MovieRepository
import ru.firstSet.kotlinOne.View.FragmentMovieDetails
import ru.firstSet.kotlinOne.View.MainActivity

class ViewModelMovieDetails() : ViewModel() {
    var coroutineScope = CoroutineScope(Dispatchers.Main)
    val movieRepository: MovieRepository = MovieRepository()

    private val movieDetailState =
        MutableLiveData<ViewModelDetailState>(ViewModelDetailState.Loading)
    val movieDetailStateLiveData: LiveData<ViewModelDetailState> get() = movieDetailState

    fun getMovie(bundle: Bundle) {
        val movie: Movie? = bundle.getParcelable<Movie>(FragmentMovieDetails.KEY_PARSE_DATA)
        var actors: List<Actor> = listOf()
        val id_movie: Long? = movie?.id
        coroutineScope.launch {
            actors = movieRepository.loadActor(id_movie)
            Log.v("actors", "${actors.size}")
            movie?.actors = actors
            movie?.let { movieDetailState.setValue(ViewModelDetailState.Success(it)) }
                ?: movieDetailState.setValue(
                    ViewModelDetailState.Error("Movie not find")
                )
        }
    }

    sealed class ViewModelDetailState {
        object Loading : ViewModelDetailState()
        data class Success(val movie: Movie) : ViewModelDetailState()
        data class Error(val error: String) : ViewModelDetailState()
    }
}
