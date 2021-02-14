package ru.firstSet.kotlinOne

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.context.startKoin
import ru.firstSet.kotlinOne.di.koinModule.movieDetailsModul
import ru.firstSet.kotlinOne.di.koinModule.movieListModul

class MyApplication : Application() {
    @KoinApiExtension
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(movieListModul)
            modules(movieDetailsModul)
        }
    }
}
