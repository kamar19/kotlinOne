package ru.firstSet.kotlinOne.repository

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.google.common.util.concurrent.ListenableFuture
import org.koin.android.ext.android.get
import org.koin.java.KoinJavaComponent.inject
import ru.firstSet.kotlinOne.data.Movie
import ru.firstSet.kotlinOne.movieList.ViewModelMoviesList
import org.koin.android.ext.android.get


//class WorkerListenable(appContext: Context, workerParams: WorkerParameters) : ListenableWorker(appContext, workerParams) {


//    override fun startWork(): ListenableFuture<Result> {
//        try {
//            //проверять время с последнего обновления БД
//            var moviesFromNet: List<Movie> = listOf()
////            val repositoryNet: RepositoryNet =  get ()
////
////            moviesFromNet =  repositoryNet.loadMoviesFromNET(seachMovie.seachMovie)
////            moviesFromNet.let {
////                movies.clear()
////                movies.addAll(moviesFromNet.sortedBy { it.ratings })
////                mutableState.setValue(ViewModelMoviesList.ViewModelListState.Success(movies))
////                repositoryDB.saveMoviesToDB(movies, seachMovie)
////            } ?: mutableState.setValue(ViewModelMoviesList.ViewModelListState.Error("Size error"))
////        }
////            var moviesFromNet: List<Movie> = listOf()
////
//
//            return kotlin.Result.success()
//        } catch (e:Exception){
//            return kotlin.Result.failure()
//        }
//    }

//}


