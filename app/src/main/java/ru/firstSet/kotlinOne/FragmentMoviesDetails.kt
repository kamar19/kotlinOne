package ru.firstSet.kotlinOne

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentMoviesDetails : Fragment() {
    private var imageViewBack: View? = null
    private var actorsList: List<Actor> = generateActors()
    private var movie: Movie? = null

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

        var tvMovieName = view.findViewById<TextView>(R.id.fmdMovieName).apply {
            text = arguments?.getString("nameMovie")
        }

        val listRecyclerView = view.findViewById<RecyclerView>(R.id.fmdRecyclerActor)
        listRecyclerView.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        listRecyclerView.adapter = ActorsAdapter(actorsList)
        imageViewBack = view.findViewById<View>(R.id.imageViewPath2).apply {
            setOnClickListener {
                activity?.onBackPressed()
            }
        }


    }

//    companion object {
//        fun newInstance(
////            nameImageView: Int,
////            imageViewLike: Boolean,
////            ratingBarRating: Int,
////            someId: String,
////            minuteTime: String,
//            nameMovie: String?,
////            tag:String,
////            review:String
//        ): FragmentMoviesList {
//            val args = Bundle()
////            args.putInt("nameImageView", nameImageView)
////            args.putBoolean("imageViewLike", imageViewLike)
////            args.putInt("ratingBarRating", ratingBarRating)
////            args.putString("someId", someId)
////            args.putString("minuteTime", minuteTime)
//            args.putString("nameMovie", nameMovie)
////            args.putString("tag", tag)
////            args.putString("review", review)
//            val fragment = FragmentMoviesList()
//            fragment.arguments = args
//            return fragment
//        }
//    }


    fun generateActors(): List<Actor> {
        return listOf(
            Actor(
                R.drawable.robert_downey,
                "Robert Downey Jr."
            ),
            Actor(
                R.drawable.chris_evans,
                "Chris Evans"
            ),
            Actor(
                R.drawable.mark_ruffalo,
                "Mark Ruffalo"
            ),
            Actor(
                R.drawable.chris_hemsworth,
                "Chris Hemsworth"
            ),
            Actor(
                R.drawable.robert_downey,
                "Robert Downey Jr."
            ),
            Actor(
                R.drawable.chris_evans,
                "Chris Evans"
            ),
            Actor(
                R.drawable.mark_ruffalo,
                "Mark Ruffalo"
            ),
            Actor(
                R.drawable.chris_hemsworth,
                "Chris Hemsworth"
            ),
            Actor(
                R.drawable.mark_ruffalo,
                "Mark Ruffalo"
            ),
            Actor(
                R.drawable.chris_hemsworth,
                "Chris Hemsworth"
            ),
            Actor(
                R.drawable.robert_downey,
                "Robert Downey Jr."
            ),
            Actor(
                R.drawable.chris_evans,
                "Chris Evans"
            ),
            Actor(
                R.drawable.mark_ruffalo,
                "Mark Ruffalo"
            ),
            Actor(
                R.drawable.chris_hemsworth,
                "Chris Hemsworth"
            )
        )
    }

}