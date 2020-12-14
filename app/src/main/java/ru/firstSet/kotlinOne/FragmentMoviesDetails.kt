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
import java.io.ObjectInputStream

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
//        idToDate(view, numId)
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

    private fun idToDate(itemView: View,id: Int) {

//            val ois: ObjectInputStream = ObjectInputStream(activity?.openFileInput("out.bin"))
//
//            val tempList: List<Movie> = ois.readObject() as List<Movie>


//            val movie: Movie = tempList[id]
            val fmdPoster: ImageView = itemView.findViewById(R.id.fmdPoster)
            val fmdPosterBack: View = itemView.findViewById(R.id.fmdPosterBack)
            val fmdTextViewTeg: TextView = itemView.findViewById(R.id.fmdTeg)

            val fmdMovieName: TextView = itemView.findViewById(R.id.fmdMovieName)
            val fmdSomeId: TextView = itemView.findViewById(R.id.fmdSomeId)
            val fmdRatingBar: RatingBar = itemView.findViewById(R.id.fmdRatingBar)
            val fmdReview: TextView = itemView.findViewById(R.id.fmdReview)
            val fmdStoryLineContent: TextView = itemView.findViewById(R.id.fmdStoryLineContent)
//
//
//
//        if (movie.poster != null)
//            fmdPoster.setImageResource(movie.poster)
//        if (movie.ratings != null)
//            fmdRatingBar.rating = movie.ratings.toFloat()
//        fmdSomeId.text = movie.someId
//        fmdMovieName.text = movie.title
//        fmdTextViewTeg.text = movie.tag
//        fmdTextViewReview.text = movie.overview
//
//        var title: String,
//        val overview: String,
//        val poster: String,
//        val backdrop: String,
//        val ratings: Float,
//        val adult: Boolean,
//        val runtime: Int,
//        val genres: List<Genre>,
//        val actors: List<Actor>

    }
}

