package ru.firstSet.kotlinOne

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import ru.firstSet.kotlinOne.Data.MovieRepository
import ru.firstSet.kotlinOne.Data.RemoteDataStore
import ru.firstSet.kotlinOne.viewModel.ViewModelMoviesList

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val movieListModule = module {
            single { RemoteDataStore() }
            single { MovieRepository(get()) }
            viewModel { ViewModelMoviesList(get()) }
        }

        //TODO: Add DetailsModule

        startKoin {
            //inject Android context
            androidContext(this@MyApplication)
            // use modules
            modules(movieListModule)
        }
    }
}