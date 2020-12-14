package ru.firstSet.kotlinOne

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import ru.firstSet.kotlinOne.Data.Movie
import ru.firstSet.kotlinOne.DataSource.GenresDataSource
import ru.firstSet.kotlinOne.DataSource.MoviesDataSource
import java.io.FileOutputStream
import java.io.ObjectOutputStream

class MainActivity : AppCompatActivity() {
    var movieList: List<Movie> = listOf()
    private var scope = CoroutineScope(
        Dispatchers.IO
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scope.launch {
            MoviesDataSource.setMoviesList(loadMovies(this@MainActivity))
//            Log.v("MoviesDataSource", MoviesDataSource.getMoviesList().size.toString())
        }
        scope.launch {
            GenresDataSource.setGenresList(loadGenres(this@MainActivity))
//            Log.v("MoviesDataSource", MoviesDataSource.getMoviesList().size.toString())
        }
//        scope.launch {
//            MoviesDataSource.setMoviesList(loadMovies(this@MainActivity))
////            Log.v("MoviesDataSource", MoviesDataSource.getMoviesList().size.toString())
//        }


        supportFragmentManager.beginTransaction()
            .add(R.id.frameLayoutContainer, FragmentMoviesList())
            .addToBackStack(FRAGMENT_TAG_MOVIES_LIST)
            .commit()
    }

    companion object {
        const val FRAGMENT_TAG_MOVIES_LIST = "MoviesList"
        const val FRAGMENT_TAG_MOVIES_DETAILS = "MoviesDetails"
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    private suspend fun loadGenres(context: Context): List<Genre> = withContext(Dispatchers.IO) {
        val data = readAssetFileToString(context, "genres.json")
        parseGenres(data)

    }

    private fun readAssetFileToString(context: Context, fileName: String): String {
        val stream = context.assets.open(fileName)
        return stream.bufferedReader().readText()
    }
}
