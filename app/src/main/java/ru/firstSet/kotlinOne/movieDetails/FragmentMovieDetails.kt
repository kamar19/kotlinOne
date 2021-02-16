package ru.firstSet.kotlinOne.movieDetails

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.firstSet.kotlinOne.R
import ru.firstSet.kotlinOne.data.Movie
import ru.firstSet.kotlinOne.utils.EventCalendar
import ru.firstSet.kotlinOne.utils.RequestPermissions

class FragmentMovieDetails : Fragment() {
    private lateinit var imageViewBack: View
    private lateinit var imageViewShape: View
    private lateinit var fmdPoster: ImageView
    private lateinit var fmdTextViewTeg: TextView
    private lateinit var fmdMovieName: TextView
    private lateinit var fmdSomeId: TextView
    private lateinit var fmdRatingBar: RatingBar
    private lateinit var fmdReview: TextView
    private lateinit var fmdStoryLineContent: TextView
    private lateinit var listRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private var scope = CoroutineScope(Dispatchers.Main)
    val viewModelDetail: ViewModelMovieDetails by viewModel()
    private lateinit var requestPermissions: RequestPermissions
    private lateinit var eventCalendar: EventCalendar
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_movies_details, container, false)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listRecyclerView = view.findViewById<RecyclerView>(R.id.fmdRecyclerActor)
        imageViewBack = view.findViewById(R.id.fmdImageViewPath)
        imageViewShape = view.findViewById(R.id.fmdImageViewShape)
        fmdTextViewTeg = view.findViewById(R.id.fmdTeg)
        fmdMovieName = view.findViewById(R.id.fmdMovieName)
        fmdSomeId = view.findViewById(R.id.fmdSomeId)
        fmdRatingBar = view.findViewById(R.id.fmdRatingBar)
        fmdPoster = view.findViewById(R.id.fmdPoster)
        fmdReview = view.findViewById(R.id.fmdReview)
        fmdStoryLineContent = view.findViewById(R.id.fmdStoryLineContent)
        progressBar = view.findViewById(R.id.progressBarMovieDetails)
        viewModelDetail.movieDetailStateLiveData.observe(viewLifecycleOwner, this::setState)
        imageViewBack = view.findViewById<View>(R.id.fmdImageViewPath).apply {
            setOnClickListener {
                activity?.supportFragmentManager?.popBackStack()
            }
        }
        arguments?.let { viewModelDetail.getMovie(it) }
        imageViewShape = view.findViewById<View>(R.id.fmdImageViewShape).apply {
            setOnClickListener {
                requestPermissions = RequestPermissions(requireActivity())
                if (requestPermissions.hasPermissions()) {
                    eventCalendar = EventCalendar(view.context, savedInstanceState, movie)
                    eventCalendar.showPickerDialog()
                    } else {
                    requestPermissions.requestPermissionWithRationale();
                }
                requestPermissions.checkPermission(
                    callbackId,
                    Manifest.permission.READ_CALENDAR,
                    Manifest.permission.WRITE_CALENDAR
                )
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateMovie(movie: Movie) {
        this.movie = movie
        listRecyclerView.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        listRecyclerView.adapter = ActorsAdapter(movie.actors)
        fmdMovieName.text = movie.title
        fmdRatingBar.rating = movie.ratings.div(2)
        fmdSomeId.text = movie.adult.toString() + "+"
        scope.launch {
            context?.let {
                Glide
                    .with(it)
                    .load(movie.backdropPicture)
                    .into(fmdPoster)
            }
        }
        fmdMovieName.text = movie.title
        fmdTextViewTeg.text = movie.genres.joinToString(separator = ", ") { it.name }
        fmdReview.text =
            movie.vote_count.toString() + " " + getString(R.string.textViewReview)
        fmdStoryLineContent.text = movie.overview
        progressBar.visibility = ProgressBar.INVISIBLE
    }

    fun setState(state: ViewModelMovieDetails.ViewModelDetailState) =
        when (state) {
            is ViewModelMovieDetails.ViewModelDetailState.Loading ->
                progressBar.visibility = ProgressBar.VISIBLE
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
        const val PERMISSION_REQUEST_CODE = 152
        val callbackId: Int = 42
        fun newInstance(id: Long) = FragmentMovieDetails().apply {
            arguments = Bundle().apply {
                putLong(KEY_PARSE_DATA, id.toLong())
            }
        }
    }
}

