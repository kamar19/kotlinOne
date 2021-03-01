package ru.firstSet.kotlinOne.movieList

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
import org.koin.android.viewmodel.ext.android.viewModel
import ru.firstSet.kotlinOne.MainActivity
import ru.firstSet.kotlinOne.data.Movie
import ru.firstSet.kotlinOne.data.SeachMovie
import ru.firstSet.kotlinOne.R
import ru.firstSet.kotlinOne.movieDetails.FragmentMovieDetails

class FragmentMoviesListPage(val seachMovie: SeachMovie) : Fragment() {
    private var listRecyclerView: RecyclerView? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var pagerTabStrip: PagerTabStrip
    val viewModel: ViewModelMoviesList  by viewModel()

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
        viewModel.stateLiveData.observe(viewLifecycleOwner, this::setState)
        viewModel.loadMovieList(seachMovie)
        progressBar = view.findViewById(R.id.progressBarTab)
        listRecyclerView = view.findViewById<RecyclerView>(R.id.fmlRecyclerViewMovies)
        listRecyclerView?.layoutManager = GridLayoutManager(activity, 2)
        listRecyclerView?.adapter =
            MoviesViewAdapter { item, viewMovie-> doOnClick(item,viewMovie) }
    }

    fun doOnClick(id: Long, viewMovie: View) {
        viewModel.stateLiveData.value?.let {
            getMovie(it, id)?.let { callFragmentMovieDetails(it.id,viewMovie) }
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

    fun getMovie(state: ViewModelMoviesList.ViewModelListState, id: Long): Movie? {
        when (state) {
            is ViewModelMoviesList.ViewModelListState.Success -> {
                updateData(state.list)
                return state.list[id.toInt()]
            }
            else -> return null
        }
    }

    fun callFragmentMovieDetails(id: Long,viewMovie: View) {
        activity?.let {
            it.supportFragmentManager.findFragmentByTag(MainActivity.FRAGMENT_TAG_MOVIES_DETAILS)
            it.supportFragmentManager
                .beginTransaction()
//                .add(R.id.frameLayoutContainer, FragmentMovieDetails.newInstance(id))
                .addSharedElement(viewMovie, resources.getString(R.string.fmdСonstraintlayoutTransitionName))
                .replace(R.id.frameLayoutContainer, FragmentMovieDetails.newInstance(id))

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