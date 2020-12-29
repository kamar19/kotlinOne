package ru.firstSet.kotlinOne.View

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import ru.firstSet.kotlinOne.Data.Movie
import ru.firstSet.kotlinOne.R
import ru.firstSet.kotlinOne.viewModel.ViewModelMovieDetails

class FragmentMovieDetails : Fragment() {
    private lateinit var imageViewBack: View
    private lateinit var fmdPoster: ImageView
    private lateinit var fmdTextViewTeg: TextView
    private lateinit var fmdMovieName: TextView
    private lateinit var fmdSomeId: TextView
    private lateinit var fmdRatingBar: RatingBar
    private lateinit var fmdReview: TextView
    private lateinit var fmdStoryLineContent: TextView
    private lateinit var listRecyclerView: RecyclerView
    private lateinit var progressBar:ProgressBar
    private var scope = CoroutineScope(Dispatchers.Main)
    val viewModel: ViewModelMovieDetails = ViewModelMovieDetails()

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
        listRecyclerView = view.findViewById<RecyclerView>(R.id.fmdRecyclerActor)
        imageViewBack = view.findViewById(R.id.fmdImageViewPath)
        fmdTextViewTeg = view.findViewById(R.id.fmdTeg)
        fmdMovieName = view.findViewById(R.id.fmdMovieName)
        fmdSomeId = view.findViewById(R.id.fmdSomeId)
        fmdRatingBar = view.findViewById(R.id.fmdRatingBar)
        fmdPoster = view.findViewById(R.id.fmdPoster)
        fmdReview = view.findViewById(R.id.fmdReview)
        fmdStoryLineContent = view.findViewById(R.id.fmdStoryLineContent)
        progressBar = view.findViewById(R.id.progressBarMovieDetails)
        viewModel.movieDetailStateLiveData.observe(viewLifecycleOwner, this::setState)
        imageViewBack = view.findViewById<View>(R.id.fmdImageViewPath).apply {
            setOnClickListener {
                activity?.supportFragmentManager?.popBackStack()
            }
        }
        arguments?.let { viewModel.getMovie(it) }
    }

    @SuppressLint("SetTextI18n")
    private fun updateMovie(movie: Movie) {
        listRecyclerView.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        listRecyclerView.adapter = ActorsAdapter(movie.actors)
        fmdMovieName.text = movie.title
        fmdRatingBar.rating = movie.ratings.div(2)
        fmdSomeId.text = "${movie.minAge}+"
        scope.launch {
            Glide
                .with(context)
                .load(movie.backdrop)
                .into(fmdPoster)
        }
        fmdMovieName.text = movie.title
        fmdTextViewTeg.text = movie.genres.joinToString(separator = ", ") { it.name }
        fmdReview.text =
            movie.votCount.toString() + " " + getString(R.string.textViewReview)
        fmdStoryLineContent.text = movie.overview
        progressBar.visibility =ProgressBar.INVISIBLE

    }

    fun setState(state: ViewModelMovieDetails.ViewModelDetailState) =
        when (state) {
            is ViewModelMovieDetails.ViewModelDetailState.Loading ->
                progressBar.visibility =ProgressBar.VISIBLE
            is ViewModelMovieDetails.ViewModelDetailState.Success ->
                updateMovie(state.movie)
            is ViewModelMovieDetails.ViewModelDetailState.Error ->
                errorMessage(state.error)
        }

    fun errorMessage(string: String?) {
        val toast = Toast.makeText(activity, string, Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    companion object {
        const val KEY_PARSE_DATA = "movieDetails"
        fun newInstance(movie: Movie) = FragmentMovieDetails().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_PARSE_DATA, movie)
            }
        }
    }
}

