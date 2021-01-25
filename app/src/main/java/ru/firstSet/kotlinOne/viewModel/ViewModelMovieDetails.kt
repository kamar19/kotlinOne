package ru.firstSet.kotlinOne.viewModel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.firstSet.kotlinOne.Data.ActorEntity
import ru.firstSet.kotlinOne.Data.Movie
import ru.firstSet.kotlinOne.MyAplication.Companion.movieRepository
import ru.firstSet.kotlinOne.View.FragmentMovieDetails

class ViewModelMovieDetails() : ViewModel() {
    var coroutineScope = CoroutineScope(Dispatchers.Main)

    private val movieDetailState =
        MutableLiveData<ViewModelDetailState>(ViewModelDetailState.Loading)
    val movieDetailStateLiveData: LiveData<ViewModelDetailState> get() = movieDetailState

    fun getMovie(bundle: Bundle) {
        val movie: Movie? = bundle.getParcelable<Movie>(FragmentMovieDetails.KEY_PARSE_DATA)
        val actors: List<ActorEntity> = listOf()
        var actors2: List<ActorEntity> = listOf()
        val id_movie: Long? = movie?.id
        if (id_movie != null) {
            coroutineScope.launch {
                movie.actors = movieRepository.readActorFromDb(id_movie)
                movie?.let { movieDetailState.setValue(ViewModelDetailState.Success(it)) }
                    ?: movieDetailState.setValue(
                        ViewModelDetailState.Error("Actors not find")
                    )
            }
            coroutineScope.launch {
                actors2 = movieRepository.loadActorFromNET(id_movie)
                Log.v("loadActorFromNET", " ${actors2.size}")

                if (actors2.size > 0)
                    movie.actors = actors2
                movie?.let { movieDetailState.setValue(ViewModelDetailState.Success(it)) }
                    ?: movieDetailState.setValue(
                        ViewModelDetailState.Error("Actors not find")
                    )
                Log.v("loadActorFromNET", " ${movie.actors.size}")

                movieRepository.saveActorToDB(movie.actors)
            }
        }
    }

    sealed class ViewModelDetailState {
        object Loading : ViewModelDetailState()
        data class Success(val movie: Movie) : ViewModelDetailState()
        data class Error(val error: String) : ViewModelDetailState()
    }
}
