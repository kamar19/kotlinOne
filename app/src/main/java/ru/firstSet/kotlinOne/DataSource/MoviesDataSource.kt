package ru.firstSet.kotlinOne.DataSource

import android.util.Log
import ru.firstSet.kotlinOne.Data.Movie


object MoviesDataSource {
    var movieList :List<Movie> = listOf()
    fun getMoviesList(): List<Movie>  =  this.movieList

    fun setMoviesList(movieList :List<Movie>){
        this.movieList=movieList
    }
}
