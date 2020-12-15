package ru.firstSet.kotlinOne

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import ru.firstSet.kotlinOne.Data.Movie
import ru.firstSet.kotlinOne.DataSource.MoviesDataSource

class MainActivity : AppCompatActivity() {
    var movieList: List<Movie> = listOf()
    private var scope = CoroutineScope(
        Dispatchers.IO
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState==null) {
            scope.launch {
                MoviesDataSource.setMoviesList(loadMovies(this@MainActivity))
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayoutContainer, FragmentMoviesList(), FRAGMENT_TAG_MOVIES_LIST)
                .commit()
        }
        else{
            supportFragmentManager.findFragmentByTag(FRAGMENT_TAG_MOVIES_LIST)
        }

    }

    companion object {
        const val FRAGMENT_TAG_MOVIES_LIST = "MoviesList"
        const val FRAGMENT_TAG_MOVIES_DETAILS = "MoviesDetails"
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

}
