package ru.firstSet.kotlinOne.View

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.firstSet.kotlinOne.Data.Movie
import ru.firstSet.kotlinOne.R
import ru.firstSet.kotlinOne.ViewModel.ViewModelMoviesList

class FragmentMoviesList(context: Context) : Fragment() {
    private val viewModel: ViewModelMoviesList = ViewModelMoviesList(context)
    private var fmlConstraintLayoutList: ConstraintLayout? = null
    private var listRecyclerView: RecyclerView? = null

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
        listRecyclerView?.layoutManager = GridLayoutManager(activity, 2)
        listRecyclerView?.adapter =
            MoviesViewAdapter { item -> doOnClick(item) } //callFragmentMovieDetails(id)
        updateData()
        fmlConstraintLayoutList =
            view.findViewById<ConstraintLayout>(R.id.fmlConstraintLayoutList).apply {
                setOnClickListener {
                    doOnClick(0)
                }
            }
    }

    private fun doOnClick(id: Int) {
        viewModel.setId(id)
    }

    private fun updateData() {
        (listRecyclerView?.adapter as? MoviesViewAdapter)?.apply {
            viewModel.moviesList.value?.let {
                bindMovie(it)
            }
        }
    }

    fun setState(state: ViewModelMoviesList.State) =
        when (state) {
            ViewModelMoviesList.State.LoadingMoviewList ->
                viewModel.loadMoviewList()
            ViewModelMoviesList.State.ShowedMoviewList ->
                updateData()
            ViewModelMoviesList.State.ShowedMoviewDetails ->
                callFragmentMovieDetails()
        }

    fun callFragmentMovieDetails() {
        val movies: Movie? = viewModel.id.value?.let { viewModel.moviesList.value?.get(it) }
        activity?.let {
            it.supportFragmentManager.findFragmentByTag(MainActivity.FRAGMENT_TAG_MOVIES_DETAILS)
            it.supportFragmentManager.beginTransaction()
                .add(R.id.frameLayoutContainer, FragmentMovieDetails.newInstance(movies))
                .addToBackStack(MainActivity.FRAGMENT_TAG_MOVIES_DETAILS)
                .commit()
        }
    }
}
