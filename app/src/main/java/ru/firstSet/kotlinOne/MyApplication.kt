package ru.firstSet.kotlinOne

import android.app.Application
import androidx.work.WorkerFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import ru.firstSet.kotlinOne.di.koinModule.movieDetailsModul
import ru.firstSet.kotlinOne.di.koinModule.movieListModul

class MyApplication : Application() {
//, KoinComponent
    override fun onCreate() {
        super.onCreate()
        startKoin {
//            workManagerFactory()
            androidContext(this@MyApplication)
            modules(movieListModul)
            modules(movieDetailsModul)

        }
//        setupWorkManagerFactory()
    }
}
//    fun Application.setupWorkManagerFactory(
//            // no vararg for WorkerFactory
//    ) {
////        . . .
////        getKoin().getAll<WorkerFactory>()
////                .forEach {
////                    delegatingWorkerFactory.addFactory(it)
////                }
////    }
//
//
//}
