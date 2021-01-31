package ru.firstSet.kotlinOne

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.firstSet.kotlinOne.di.koinModule.movieListModul

class MyAplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyAplication)
            modules(movieListModul)
        }
    }
}