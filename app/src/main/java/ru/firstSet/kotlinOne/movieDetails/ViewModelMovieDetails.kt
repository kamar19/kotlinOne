package ru.firstSet.kotlinOne.movieDetails

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.firstSet.kotlinOne.data.Movie
import ru.firstSet.kotlinOne.repository.RepositoryDB
import ru.firstSet.kotlinOne.repository.RepositoryNet

class ViewModelMovieDetails(val repositoryNet: RepositoryNet,val repositoryDB: RepositoryDB) :
    ViewModel() {
    var coroutineScope = CoroutineScope(Dispatchers.Main)

    private val movieDetailState =
        MutableLiveData<ViewModelDetailState>(ViewModelDetailState.Loading)
    val movieDetailStateLiveData: LiveData<ViewModelDetailState> get() = movieDetailState

    fun getMovie(bundle: Bundle) {
        val id: Long = bundle.getLong(FragmentMovieDetails.KEY_PARSE_DATA)
        var movie: Movie
        coroutineScope.launch {
            movie = repositoryDB.readMovieFromDb(id)
            movieDetailState.setValue(ViewModelDetailState.Success(movie))
        }
        coroutineScope.launch {
            movie = repositoryNet.loadMovieFromNET(id)
            movie.actors = repositoryNet.loadActorFromNET(id)
            if (movie.actors.size == 0) {
                movieDetailState.setValue(ViewModelDetailState.Error("Actors not find"))
            }
            if (movie.id > 0) {
                movieDetailState.setValue(ViewModelDetailState.Success(movie))
            } else {
                movieDetailState.setValue(
                    ViewModelDetailState.Error("Movie not find")
                )
            }
        }
    }

    sealed class ViewModelDetailState {
        object Loading : ViewModelDetailState()
        data class Success(val movie: Movie) : ViewModelDetailState()
        data class Error(val error: String) : ViewModelDetailState()
    }
}