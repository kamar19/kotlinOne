package ru.firstSet.kotlinOne.View

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.firstSet.kotlinOne.Data.Movie
import ru.firstSet.kotlinOne.R
import ru.firstSet.kotlinOne.ViewModel.ViewModelMovieDetails

class FragmentMovieDetails : Fragment() {
    private var scope = CoroutineScope(Dispatchers.Main)
    private var imageViewBack: View? = null
    private var fmdPoster: ImageView? = null
    val viewModel:ViewModelMovieDetails by lazy { ViewModelMovieDetails(arguments?.getParcelable<Movie>(KEY_PARSE_DATA))}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_movies_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.movieDetailStateLiveData.observe(viewLifecycleOwner, this::setState)
        updateMovie(view, viewModel.movieDetailMovie.value)
        val listRecyclerView = view.findViewById<RecyclerView>(R.id.fmdRecyclerActor)
        listRecyclerView.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        listRecyclerView.adapter =
            viewModel.movieDetailMovie.value?.let { ActorsAdapter(it.actors) }
        imageViewBack = view.findViewById<View>(R.id.fmdImageViewPath).apply {
            setOnClickListener {
                viewModel.changeStateMovieDetail()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateMovie(itemView: View, movie: Movie?) {
        val fmdTextViewTeg: TextView = itemView.findViewById(R.id.fmdTeg)
        val fmdMovieName: TextView = itemView.findViewById(R.id.fmdMovieName)
        val fmdSomeId: TextView = itemView.findViewById(R.id.fmdSomeId)
        val fmdRatingBar: RatingBar = itemView.findViewById(R.id.fmdRatingBar)
        val fmdReview: TextView = itemView.findViewById(R.id.fmdReview)
        val fmdStoryLineContent: TextView = itemView.findViewById(R.id.fmdStoryLineContent)
        fmdPoster = itemView.findViewById(R.id.fmdPoster)
        fmdMovieName.text = movie?.title
        fmdRatingBar.rating = movie?.ratings?.div(2)!!
        fmdSomeId.text = "${movie.minAge}+"
        scope.launch {
            Glide
                .with(itemView)
                .load(movie.backdrop)
                .into(fmdPoster)
        }
        fmdMovieName.text = movie.title
        fmdTextViewTeg.text = movie.genres.joinToString(separator = ", ") { it.name }
        fmdReview.text =
            movie.votCount.toString() + " " + getString(R.string.textViewReview)
        fmdStoryLineContent.text = movie.overview
    }

    fun setState(state: ViewModelMovieDetails.State) =
        when (state) {
            ViewModelMovieDetails.State.ShowedMoviewDetails ->
                this.view?.let { updateMovie(it, viewModel.movieDetailMovie.value) }
            ViewModelMovieDetails.State.ShowedMoviewList ->
                activity?.supportFragmentManager?.popBackStack()
        }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    companion object {
        const val KEY_PARSE_DATA = "movieDetails"
        fun newInstance(movie: Movie?) = FragmentMovieDetails().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_PARSE_DATA, movie)
            }
        }
    }
}

