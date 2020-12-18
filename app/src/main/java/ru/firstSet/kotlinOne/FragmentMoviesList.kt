package ru.firstSet.kotlinOne

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import ru.firstSet.kotlinOne.Data.Movie

class FragmentMoviesList : Fragment() {
    private var fmlConstraintLayoutList: ConstraintLayout? = null
    private var moviesList: List<Movie> = listOf()
    private var listRecyclerView: RecyclerView? = null
    private var scope = CoroutineScope(
        Dispatchers.Main
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        scope.launch {
            moviesList = loadMovies(inflater.context)
            updateData()
        }
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listRecyclerView = view.findViewById<RecyclerView>(R.id.fmlRecyclerViewMovies)
        listRecyclerView?.layoutManager = GridLayoutManager(activity, 2)
        listRecyclerView?.adapter = MoviesViewAdapter { item -> doOnClick(item) }
        fmlConstraintLayoutList =
            view.findViewById<ConstraintLayout>(R.id.fmlConstraintLayoutList).apply {
                setOnClickListener {
                    callFragmentMovieDetails(0)
                }
            }
    }

    fun callFragmentMovieDetails(id: Int) {
        activity?.let {
            it.supportFragmentManager.findFragmentByTag(MainActivity.FRAGMENT_TAG_MOVIES_DETAILS)
            it.supportFragmentManager.beginTransaction()
                .add(R.id.frameLayoutContainer, FragmentMoviesDetails.newInstance(moviesList[id]))
                .addToBackStack(MainActivity.FRAGMENT_TAG_MOVIES_DETAILS)
                .commit()
        }
    }

    private fun doOnClick(id: Int) {
        callFragmentMovieDetails(id)
    }

    private fun updateData() {
        (listRecyclerView?.adapter as? MoviesViewAdapter)?.apply {
            bindMovie(moviesList)
        }
    }
}









