package ru.firstSet.kotlinOne

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.firstSet.kotlinOne.Data.Movie
import ru.firstSet.kotlinOne.DataSource.MoviesDataSource
import java.io.ObjectInputStream


class FragmentMoviesList() : Fragment() {
    private var fmlConstraintLayoutList: ConstraintLayout? = null
    private var moviesList: List<Movie> = listOf()
    private var listRecyclerView: RecyclerView? = null
    private var scope = CoroutineScope(
        Dispatchers.Main
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

//            var movieData: MoviesDataSource = movieList
//            Json.toString() .encodeToString(Data(42))


    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        scope.launch {
//            MoviesDataSource.setMoviesList(loadMovies(view.context))
//            Log.v("MoviesDataSource", MoviesDataSource.getMoviesList().size.toString())
//        }


        listRecyclerView = view.findViewById<RecyclerView>(R.id.fmlRecyclerViewMovies)
        listRecyclerView?.layoutManager = GridLayoutManager(activity, 2)
        listRecyclerView?.adapter = MoviesViewAdapter { item -> doOnClick(item) }
        fmlConstraintLayoutList =
            view.findViewById<ConstraintLayout>(R.id.fmlConstraintLayoutList).apply {
                setOnClickListener {
                    callFragmentMovieDetails(1)
                }
            }
    }

    fun callFragmentMovieDetails(id: Int) {
        val frarmentMoviesDetails: Fragment = FragmentMoviesDetails()
        val bundle = Bundle()
        bundle.putInt("ID", id)
        frarmentMoviesDetails.arguments = bundle
        activity?.let {
            it.supportFragmentManager.findFragmentByTag(MainActivity.FRAGMENT_TAG_MOVIES_DETAILS)
            it.supportFragmentManager.beginTransaction()
                .add(R.id.frameLayoutContainer, frarmentMoviesDetails)
                .addToBackStack(MainActivity.FRAGMENT_TAG_MOVIES_DETAILS)
                .commit()
        }
    }

    private fun doOnClick(id: Int) {
        callFragmentMovieDetails(id)
    }

    override fun onStart() {
        super.onStart()
        updateData()
    }


    private fun updateData() {
        (listRecyclerView?.adapter as? MoviesViewAdapter)?.apply {
            scope.launch {
                bindMovie(ru.firstSet.kotlinOne.DataSource.MoviesDataSource.getMoviesList())
            }




        }
    }
    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}









