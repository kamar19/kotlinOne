package ru.firstSet.kotlinOne.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.firstSet.kotlinOne.movieDetails.ViewModelMovieDetails
import ru.firstSet.kotlinOne.movieList.ViewModelMoviesList
import ru.firstSet.kotlinOne.repository.*

object koinModule {
    val movieListModul = module {
        single { RemoteDataStore() }
        single { MovieDatabase.createMovieDatabaseInstance(get()) }
        single { RepositoryDB(get()) }
        single { RepositoryNet(get()) }
        viewModel { ViewModelMoviesList(get(),get()) }
        viewModel { ViewModelMovieDetails(get(),get()) }

    }
}