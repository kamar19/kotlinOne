package ru.firstSet.kotlinOne.DataSource

import android.util.Log
import ru.firstSet.kotlinOne.Data.Movie


object MoviesDataSource {
    var movieList :List<Movie> = listOf()
    fun getMoviesList(): List<Movie> {
        Log.v("getMoviesList()",movieList.size.toString())
        return this.movieList
    }
    fun setMoviesList(movieList :List<Movie>){
        Log.v("setMoviesList()", MoviesDataSource.movieList.size.toString())
        this.movieList=movieList
    }
}
