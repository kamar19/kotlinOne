package ru.firstSet.kotlinOne

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
import ru.firstSet.kotlinOne.DataSource.MoviesDataSource

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
        idToDate(view, arguments?.getInt("ID"))
//        if (movie?.ratings?.toInt()?.equals(5)!!) {
            val listRecyclerView = view.findViewById<RecyclerView>(R.id.fmdRecyclerActor)
            listRecyclerView.layoutManager =
                LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            listRecyclerView.adapter = movie?.let { ActorsAdapter(it.actors) }
            imageViewBack = view.findViewById<View>(R.id.fmdImageViewPath).apply {
                setOnClickListener {
                    activity?.supportFragmentManager?.popBackStack()
                }
            }
        //}
    }


    @SuppressLint("SetTextI18n")
    private fun idToDate(itemView: View, id: Int?) {
        val movieList: List<Movie> = MoviesDataSource.getMoviesList()
        val fmdTextViewTeg: TextView = itemView.findViewById(R.id.fmdTeg)
        val fmdMovieName: TextView = itemView.findViewById(R.id.fmdMovieName)
        val fmdSomeId: TextView = itemView.findViewById(R.id.fmdSomeId)
        val fmdRatingBar: RatingBar = itemView.findViewById(R.id.fmdRatingBar)
        val fmdReview: TextView = itemView.findViewById(R.id.fmdReview)
        val fmdStoryLineContent: TextView = itemView.findViewById(R.id.fmdStoryLineContent)
        if (id != null) movie = movieList[id]
        fmdPoster = itemView.findViewById(R.id.fmdPoster)
        fmdMovieName.text = movie?.title
        fmdRatingBar.rating = movie?.ratings?.div(2)!!
        fmdSomeId.text = "${movie?.minAge}+"
        scope.launch {
            Glide
                .with(itemView)
                .load(movie?.backdrop)
                .into(fmdPoster)
        }
        fmdMovieName.text = movie?.title
        fmdTextViewTeg.text = movie?.genres?.joinToString(separator = ", ") { it.name }
        fmdReview.text =
            movie?.votCount.toString() + " " + itemView.context.getString(R.string.textViewReview)
        fmdStoryLineContent.text = movie?.overview
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    companion object {
        fun newInstance(id: Int) = FragmentMoviesDetails().apply {
            arguments = Bundle().apply { putInt("ID", id) }
        }
    }
}

