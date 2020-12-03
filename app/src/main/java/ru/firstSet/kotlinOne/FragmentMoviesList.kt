package ru.firstSet.kotlinOne

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentMoviesList() : Fragment() {
    private var fmlConstraintLayoutList: ConstraintLayout? = null

    private var moviesList: List<Movie> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moviesList = generateMovies()
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View  = inflater.inflate(R.layout.fragment_movies_list, container, false)
        return view
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // устанавливаем для списка адаптер
        // устанавливаем для списка адаптер
        val listRecyclerView = view.findViewById<RecyclerView>(R.id.fmlRecyclerViewMovies)
        listRecyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            //LinearLayoutManager занимается размещением объектов на экране и прокруткой
        listRecyclerView.adapter = MoviesViewAdapter(this.context!!, moviesList)





   fmlConstraintLayoutList =
            view.findViewById<ConstraintLayout>(R.id.fmlConstraintLayoutList).apply {
//                setOnClickListener {
//                    activity?.let{ it.supportFragmentManager.findFragmentByTag(MainActivity.FRAGMENT_TAG_MOVIES_DETAILS)
//                        it.supportFragmentManager.beginTransaction()
//                                .add(R.id.frameLayoutContainer, FragmentMoviesDetails())
//                                .addToBackStack(MainActivity.FRAGMENT_TAG_MOVIES_DETAILS)
//                                .commit()
//                        }
//                }
//            }
    }
                //уберу временно 2-й фрагмент
//                setOnClickListener {
//                    activity?.let {
//                        it.supportFragmentManager.findFragmentByTag(MainActivity.FRAGMENT_TAG_MOVIES_DETAILS)
//                        it.supportFragmentManager.beginTransaction()
//                            .add(R.id.frameLayoutContainer, FragmentMoviesDetails())
//                            .addToBackStack(MainActivity.FRAGMENT_TAG_MOVIES_DETAILS)
//                            .commit()
//                    }
                }
            }





    fun generateMovies(): List<Movie> {
        return listOf(
            Movie(
//                "avengers_end_game",
                "Avengers: End Game",
                "13+",
//                false,
                "137 min",
//                4,
                "Action, Adventure, Drama",
                "125 Reviews"
            ),
            Movie(
//                "tenet",
                "Tenet",
                "16+",
//                true,
                "97 min",
//                5,
                "Action, Sci-Fi, Thriller",
                "98 Reviews"
            ),
            Movie(
//                "black_widow",
                "Black Widow",
                "13+",
//                false,
                "102 min",
//                4,
                "Action, Adventure, Sci-Fi",
                "38 Reviews"
            ),
            Movie(
//                "wonder_woman_1984",
                "Wonder Woman 1984",
                "13+",
//                false,
                "120 min",
//                5,
                "Action, Adventure, Fantasy",
                "74 Reviews"
            )
        )
    }





