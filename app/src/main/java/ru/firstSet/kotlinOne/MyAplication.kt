package ru.firstSet.kotlinOne

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import ru.firstSet.kotlinOne.repository.MovieDatabase
import ru.firstSet.kotlinOne.repository.Repositorys
import ru.firstSet.kotlinOne.movieDetails.ViewModelMovieDetails
import ru.firstSet.kotlinOne.movieList.ViewModelMoviesList
import ru.firstSet.kotlinOne.repository.RemoteDataStore

class MyAplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val  movieListModul  =  module {
            single { RemoteDataStore() }
            viewModel { ViewModelMoviesList(get() )}
            viewModel { ViewModelMovieDetails(get() ) }
            single { MovieDatabase.createMovieDatabaseInstance(get()) }
            single { Repositorys(get (),get()) }
        }
        startKoin {
            androidContext(this@MyAplication)
            modules(movieListModul)
        }
    }
}