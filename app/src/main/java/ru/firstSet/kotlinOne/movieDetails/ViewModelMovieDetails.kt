package ru.firstSet.kotlinOne.movieDetails

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.firstSet.kotlinOne.data.ActorEntity
import ru.firstSet.kotlinOne.data.Movie
import ru.firstSet.kotlinOne.repository.Repositorys

class ViewModelMovieDetails(val repositorys: Repositorys) :
    ViewModel() {
    var coroutineScope = CoroutineScope(Dispatchers.Main)

    private val movieDetailState =
        MutableLiveData<ViewModelDetailState>(ViewModelDetailState.Loading)
    val movieDetailStateLiveData: LiveData<ViewModelDetailState> get() = movieDetailState

    fun getMovie(bundle: Bundle) {
        val movie: Movie? = bundle.getParcelable<Movie>(FragmentMovieDetails.KEY_PARSE_DATA)
        var actors: List<ActorEntity> = listOf()

        movie?.let {
            coroutineScope.launch {
                movie.actors = repositorys.readActorFromDb(movie.id)
                movieDetailState.setValue(ViewModelDetailState.Success(it))
            }
            coroutineScope.launch {
                actors = repositorys.loadActorFromNET(movie.id)
                if (actors.size > 0) {
                    movie.actors = actors
                    movieDetailState.setValue(ViewModelDetailState.Success(it))
                    repositorys.saveActorToDB(movie.actors)
                }
            }
        } ?: movieDetailState.setValue(
            ViewModelDetailState.Error("Actors not find")
        )
    }

    sealed class ViewModelDetailState {
        object Loading : ViewModelDetailState()
        data class Success(val movie: Movie) : ViewModelDetailState()
        data class Error(val error: String) : ViewModelDetailState()
    }
}