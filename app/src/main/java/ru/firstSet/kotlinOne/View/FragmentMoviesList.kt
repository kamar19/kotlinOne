package ru.firstSet.kotlinOne.View

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.firstSet.kotlinOne.Data.Movie
import ru.firstSet.kotlinOne.R
import ru.firstSet.kotlinOne.viewModel.ViewModelMoviesList

class FragmentMoviesList() : Fragment() {
    private val viewModel: ViewModelMoviesList = ViewModelMoviesList()
    private var fmlConstraintLayoutList: ConstraintLayout? = null
    private var listRecyclerView: RecyclerView? = null
    private lateinit var progressBar: ProgressBar

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
        viewModel.stateLiveData.observe(viewLifecycleOwner, this::setState)
        listRecyclerView = view.findViewById<RecyclerView>(R.id.fmlRecyclerViewMovies)
        progressBar = view.findViewById(R.id.progressBar)
        this.context?.let { viewModel.loadMoviewList(it) }
        listRecyclerView?.layoutManager = GridLayoutManager(activity, 2)
        listRecyclerView?.adapter =
            MoviesViewAdapter { item -> doOnClick(item) }
        fmlConstraintLayoutList =
            view.findViewById<ConstraintLayout>(R.id.fmlConstraintLayoutList).apply {
                setOnClickListener {
                    doOnClick(0)
                }
            }
    }

    private fun doOnClick(id: Int) {
        viewModel.stateLiveData.value?.let { getMovie(it, id)?.let { callFragmentMovieDetails(it) } }
    }

    private fun updateData(movieList: List<Movie>) {
        (listRecyclerView?.adapter as? MoviesViewAdapter)?.apply {
            bindMovie(movieList)
        }
        progressBar.visibility =ProgressBar.INVISIBLE
    }

    fun setState(state: ViewModelMoviesList.ViewModelListState) =
        when (state) {
            is ViewModelMoviesList.ViewModelListState.Loading ->
            progressBar.visibility =ProgressBar.VISIBLE
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
}
