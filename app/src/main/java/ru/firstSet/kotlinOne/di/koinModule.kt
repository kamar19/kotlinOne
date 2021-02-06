package ru.firstSet.kotlinOne.di

import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import ru.firstSet.kotlinOne.repository.RemoteDataStore
import ru.firstSet.kotlinOne.movieDetails.ViewModelMovieDetails
import ru.firstSet.kotlinOne.movieList.ViewModelMoviesList
import ru.firstSet.kotlinOne.repository.*
import ru.firstSet.kotlinOne.worker.RepositorySP

@KoinApiExtension
object koinModule {
    val movieListModul = module {
        single { RemoteDataStore() }
        single { MovieDatabase.createMovieDatabaseInstance(get()) }
        single { RepositoryDB(get()) }
        single { RepositoryNet(get()) }
        single { RepositorySP(androidContext()) }
        viewModel { ViewModelMoviesList(get(), get(), get()) }
    }
    val movieDetailsModul = module {
        viewModel { ViewModelMovieDetails(get(), get()) }
    }
}
