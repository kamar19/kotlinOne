package ru.firstSet.kotlinOne.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.firstSet.kotlinOne.movieDetails.ViewModelMovieDetails
import ru.firstSet.kotlinOne.movieList.ViewModelMoviesList
import ru.firstSet.kotlinOne.repository.MovieDatabase
import ru.firstSet.kotlinOne.repository.RemoteDataStore
import ru.firstSet.kotlinOne.repository.RepositoryData

object koinModule {
    val  movieListModul  =  module {
        single { RemoteDataStore() }
        viewModel { ViewModelMoviesList(get() ) }
        viewModel { ViewModelMovieDetails(get() ) }
        single { MovieDatabase.createMovieDatabaseInstance(get()) }
        single { RepositoryData(get (),get()) }
    }
}