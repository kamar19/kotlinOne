package ru.firstSet.kotlinOne

import android.app.Application
import ru.firstSet.kotlinOne.Data.MovieRepository

class MyAplication : Application() {

    companion object {
        lateinit var movieRepository: MovieRepository
    }

    override fun onCreate() {
        super.onCreate()
        movieRepository = MovieRepository(applicationContext)
    }

}