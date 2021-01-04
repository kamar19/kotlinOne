package ru.firstSet.kotlinOne.viewModel

import android.content.Context
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.firstSet.kotlinOne.Data.Movie
import ru.firstSet.kotlinOne.Data.RetrofitMovie
import ru.firstSet.kotlinOne.Data.RetrofitMovie.Companion.BASE_URL_MOVIES
import ru.firstSet.kotlinOne.JsonMovie
import ru.firstSet.kotlinOne.loadMovies


class ViewModelMoviesList : ViewModel() {
    private var scope = viewModelScope
    private val mutableState = MutableLiveData<ViewModelListState>(ViewModelListState.Loading)
    val stateLiveData: LiveData<ViewModelListState> get() = mutableState
    var retrofitMovie:RetrofitMovie = RetrofitMovie()
    fun loadMoviewList(context: Context):List<Movie> {
        var oldMoviesList = listOf<Movie>()

        scope.launch {
            oldMoviesList = loadMovies(context)

            val newMoviesList = retrofitMovie.loadMovies()

            for (i in 0..newMoviesList.size-1){
                oldMoviesList[i].posterPicture  = BASE_URL_MOVIES+newMoviesList[i].posterPicture
                oldMoviesList[i].title = newMoviesList[i].title
//                oldMoviesList[i].overview = newMoviesList[i].overview
//                oldMoviesList[i].ratings = newMoviesList[i].ratings
            }

            if (oldMoviesList.isEmpty()) { mutableState.setValue(ViewModelListState.Error("Size error"))}
            else
            { mutableState.setValue(ViewModelListState.Success(oldMoviesList))}

        }
        return oldMoviesList
    }

    sealed class ViewModelListState {
        object Loading : ViewModelListState()
        data class Success(val list: List<Movie>) : ViewModelListState()
        data class Error(val error: String) : ViewModelListState()
    }




}
