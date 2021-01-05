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
        var newMoviesList: List<Movie> = listOf()
        var oldMovieList: List<Movie>  = listOf()

        scope.launch {
            oldMovieList = loadMovies(context)
            newMoviesList = retrofitMovie.loadMovies()
            for ( i in 0..newMoviesList.size-1)
            {
                oldMovieList[i].title = newMoviesList[i].title
                oldMovieList[i].posterPicture = newMoviesList[i].posterPicture
                oldMovieList[i].backdropPicture = newMoviesList[i].backdropPicture
                oldMovieList[i].ratings = newMoviesList[i].ratings
                oldMovieList[i].overview = newMoviesList[i].overview
//                oldMovieList[i].adult = if (newMoviesList[i].adult) 16 else 13
                oldMovieList[i].adult = newMoviesList[i].adult
                oldMovieList[i].vote_count= newMoviesList[i].vote_count
            }


            if (newMoviesList.isEmpty()) { mutableState.setValue(ViewModelListState.Error("Size error"))}
            else
            { mutableState.setValue(ViewModelListState.Success(oldMovieList))}

        }
        return oldMovieList
    }

    sealed class ViewModelListState {
        object Loading : ViewModelListState()
        data class Success(val list: List<Movie>) : ViewModelListState()
        data class Error(val error: String) : ViewModelListState()
    }




}
