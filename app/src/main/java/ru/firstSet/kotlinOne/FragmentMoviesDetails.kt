package ru.firstSet.kotlinOne

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
import ru.firstSet.kotlinOne.Data.Actor
import ru.firstSet.kotlinOne.Data.Movie
import ru.firstSet.kotlinOne.DataSource.MoviesDataSource

class FragmentMoviesDetails : Fragment() {
    private var imageViewBack: View? = null
    private var actorsList: List<Actor> = listOf()
    private var numId: Int = 1

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
        val bundle = arguments
        if (bundle != null)
            numId = bundle.getInt("ID")
        idToDate(view, numId)
        val listRecyclerView = view.findViewById<RecyclerView>(R.id.fmdRecyclerActor)
        listRecyclerView.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        listRecyclerView.adapter = ActorsAdapter(actorsList)
        imageViewBack = view.findViewById<View>(R.id.fmdImageViewPath).apply {
            setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    private fun idToDate(itemView: View, id: Int) {
        val tempList: List<Movie> = MoviesDataSource().getMoviesList()
        val movie: Movie = tempList[id]
        val imageViewPoster: ImageView = itemView.findViewById(R.id.fmdPoster)
        val textViewTag: TextView = itemView.findViewById(R.id.fmdTextViewTeg)
        val textViewMovieName: TextView = itemView.findViewById(R.id.fmdMovieName)
        val textViewSomeId: TextView = itemView.findViewById(R.id.fmdSomeId)
        val ratingBarRating: RatingBar = itemView.findViewById(R.id.fmdRatingBar)
        val textViewReview: TextView = itemView.findViewById(R.id.fmdTextViewReview)
        if (movie.nameImageView != null)
            imageViewPoster.setImageResource(movie.nameImageView)
        if (movie.ratingBarRating != null)
            ratingBarRating.rating = movie.ratingBarRating.toFloat()
        textViewSomeId.text = movie.someId
        textViewMovieName.text = movie.nameMovie
        textViewTag.text = movie.tag
        textViewReview.text = movie.review
    }
}

