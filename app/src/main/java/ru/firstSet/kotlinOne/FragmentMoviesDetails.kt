package ru.firstSet.kotlinOne

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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

class FragmentMoviesDetails : Fragment() {
    private var imageViewBack: View? = null
    private var movie: Movie? = null
    private var numId: Int = 1
    private var fmdPoster: ImageView? = null
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
    ): View = inflater.inflate(R.layout.fragment_movies_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movie = arguments?.getParcelable<Movie>(KEY_PARSE_DATA)
        idToDate(view, movie)
        val listRecyclerView = view.findViewById<RecyclerView>(R.id.fmdRecyclerActor)
        listRecyclerView.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        listRecyclerView.adapter = movie?.let { ActorsAdapter(it.actors) }
        imageViewBack = view.findViewById<View>(R.id.fmdImageViewPath).apply {
            setOnClickListener {
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun idToDate(itemView: View, movie: Movie?) {
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
            movie.votCount.toString() + " " + itemView.context.getString(R.string.textViewReview)
        fmdStoryLineContent.text = movie.overview
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    companion object {
        const val KEY_PARSE_DATA = "movieDetails"
        fun newInstance(movie: Movie) = FragmentMoviesDetails().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_PARSE_DATA, movie)
            }
        }
    }
}

