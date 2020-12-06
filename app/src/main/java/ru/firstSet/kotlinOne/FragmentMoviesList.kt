package ru.firstSet.kotlinOne

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.firstSet.kotlinOne.Data.Movie
import ru.firstSet.kotlinOne.DataSource.MoviesDataSource

class FragmentMoviesList() : Fragment(), MoviesViewAdapter.SomeInterfaceClickListener {
    private var fmlConstraintLayoutList: ConstraintLayout? = null
    private var moviesList: List<Movie> = listOf()
    private var listRecyclerView: RecyclerView? = null
    private val clickListener = object : MoviesViewAdapter.SomeInterfaceClickListener {
        override fun onClick(id: Int) {
            doOnClick(id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listRecyclerView = view.findViewById<RecyclerView>(R.id.fmlRecyclerViewMovies)
        listRecyclerView?.layoutManager = GridLayoutManager(activity, 2)
        listRecyclerView?.adapter = MoviesViewAdapter(clickListener, moviesList)
        fmlConstraintLayoutList =
            view.findViewById<ConstraintLayout>(R.id.fmlConstraintLayoutList).apply {
                setOnClickListener {
                    callFragmentMovieDetails(1)
                }
            }
    }

    override fun onClick(id: Int) {
        doOnClick(id)
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
            bindMovie(MoviesDataSource().getMoviesList())
        }
    }
}









