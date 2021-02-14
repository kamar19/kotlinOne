package ru.firstSet.kotlinOne.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import ru.firstSet.kotlinOne.data.MovieNotifications
import ru.firstSet.kotlinOne.data.Notifications
import ru.firstSet.kotlinOne.repository.RemoteDataStore
import ru.firstSet.kotlinOne.movieDetails.ViewModelMovieDetails
import ru.firstSet.kotlinOne.movieList.ViewModelMoviesList
import ru.firstSet.kotlinOne.repository.*

@KoinApiExtension
object koinModule {
    val movieListModul = module {
        single { RemoteDataStore() }
        single { MovieDatabase.createMovieDatabaseInstance(get()) }
        single { MovieNotifications(get()) }
        single { RepositoryDB(get()) }
        single { RepositoryNet(get()) }
        viewModel { ViewModelMoviesList(get(), get()) }
    }
    val movieDetailsModul = module {
        viewModel { ViewModelMovieDetails(get(), get()) }
    }
}
