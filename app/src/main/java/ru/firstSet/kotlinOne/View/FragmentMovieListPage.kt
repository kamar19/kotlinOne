package ru.firstSet.kotlinOne.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerTabStrip
import ru.firstSet.kotlinOne.Data.Movie
import ru.firstSet.kotlinOne.Data.SeachMovie
import ru.firstSet.kotlinOne.R
import ru.firstSet.kotlinOne.viewModel.ViewModelMoviesList


class FragmentMoviesListPage(val seachMovie: SeachMovie) : Fragment() {
    private var listRecyclerView: RecyclerView? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var pagerTabStrip: PagerTabStrip
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.view_pager_tab, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainActivity.viewModel.stateLiveData.observe(viewLifecycleOwner, this::setState)
        MainActivity.viewModel.loadMoviewList(seachMovie)
        progressBar = view.findViewById(R.id.progressBarTab)
        listRecyclerView = view.findViewById<RecyclerView>(R.id.fmlRecyclerViewMovies)
        listRecyclerView?.layoutManager = GridLayoutManager(activity, 2)
        listRecyclerView?.adapter =
            MoviesViewAdapter { item -> doOnClick(item) }
    }

    fun doOnClick(id: Int) {
        MainActivity.viewModel.stateLiveData.value?.let {
            getMovie(it, id)?.let { callFragmentMovieDetails(it) }
        }
    }

    private fun updateData(movieList: List<Movie>) {
        (listRecyclerView?.adapter as? MoviesViewAdapter)?.apply {
            bindMovie(movieList)
        }
        progressBar.visibility = ProgressBar.INVISIBLE
    }

    fun setState(state: ViewModelMoviesList.ViewModelListState) =
        when (state) {
            is ViewModelMoviesList.ViewModelListState.Loading ->
                progressBar.visibility = ProgressBar.VISIBLE
            is ViewModelMoviesList.ViewModelListState.Success -> {
                updateData(state.list)
            }
            is ViewModelMoviesList.ViewModelListState.Error ->
                errorMessage(state.error)
        }

    fun getMovie(state: ViewModelMoviesList.ViewModelListState, id: Int): Movie? {
        when (state) {
            is ViewModelMoviesList.ViewModelListState.Success -> {
                updateData(state.list)
                return state.list[id]
            }
            else -> return null
        }
    }

    fun callFragmentMovieDetails(movie: Movie) {
        activity?.let {
            it.supportFragmentManager.findFragmentByTag(MainActivity.FRAGMENT_TAG_MOVIES_DETAILS)
            it.supportFragmentManager.beginTransaction()
                .add(R.id.frameLayoutContainer, FragmentMovieDetails.newInstance(movie))
                .addToBackStack(MainActivity.FRAGMENT_TAG_MOVIES_DETAILS)
                .commit()
        }
    }

    fun errorMessage(string: String?) {
        val toast = Toast.makeText(activity, string, Toast.LENGTH_SHORT)
        toast.show()
    }

    companion object {
        const val KEY_PARSE_DATA = "moviePage"
        fun newInstance(seachMovie: SeachMovie) = FragmentMovieDetails().apply {
            arguments = Bundle().apply {
                putParcelable(FragmentMovieDetails.KEY_PARSE_DATA, seachMovie)
            }
        }
    }
}