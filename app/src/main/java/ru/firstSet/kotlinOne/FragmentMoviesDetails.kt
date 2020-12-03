package ru.firstSet.kotlinOne

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentMoviesDetails : Fragment() {
    private var imageViewBack: View? = null
    private var actorsList: List<Actor> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actorsList = generateActors()
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_movies_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listRecyclerView = view.findViewById<RecyclerView>(R.id.fmdRecyclerActor)
        listRecyclerView.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        listRecyclerView.adapter = ActorsAdapter(this.context!!, actorsList)
        imageViewBack = view.findViewById<View>(R.id.imageViewPath2).apply {
            setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }
}

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
        )
    )
}

