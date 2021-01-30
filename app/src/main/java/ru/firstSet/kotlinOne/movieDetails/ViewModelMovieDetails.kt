package ru.firstSet.kotlinOne.movieDetails

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.firstSet.kotlinOne.data.Movie
import ru.firstSet.kotlinOne.repository.RepositoryData

class ViewModelMovieDetails(val repositoryData: RepositoryData) :
    ViewModel() {
    var coroutineScope = CoroutineScope(Dispatchers.Main)

    private val movieDetailState =
        MutableLiveData<ViewModelDetailState>(ViewModelDetailState.Loading)
    val movieDetailStateLiveData: LiveData<ViewModelDetailState> get() = movieDetailState

    fun getMovie(bundle: Bundle) {
        val id: Long = bundle.getLong(FragmentMovieDetails.KEY_PARSE_DATA)
        var movie: Movie
        coroutineScope.launch {

            movie = repositoryData.readMovieFromDb(id)
//            movie.actors = repositoryData.readActorFromDb(id)
            Log.v("actor.size", "${movie.actors.size}")

                movieDetailState.setValue(ViewModelDetailState.Success(movie))
        }
//        coroutineScope.launch {
//            movie = repositoryData.loadMovieFromNET(id)
//            movie.actors = repositoryData.loadActorFromNET(id)
//            if (movie.actors.size > 0) {
//                repositoryData.saveActorToDB(movie.actors)
//            } else {
//                movieDetailState.setValue(ViewModelDetailState.Error("Actors not find"))
//            }
//            if (movie.id > 0) {
//                movieDetailState.setValue(ViewModelDetailState.Success(movie))
//            } else {
//                movieDetailState.setValue(
//                    ViewModelDetailState.Error("Movie not find")
//                )
//            }
//        }
    }

    sealed class ViewModelDetailState {
        object Loading : ViewModelDetailState()
        data class Success(val movie: Movie) : ViewModelDetailState()
        data class Error(val error: String) : ViewModelDetailState()
    }
}