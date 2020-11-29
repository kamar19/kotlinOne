package ru.firstSet.kotlinOne

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment

class FragmentMoviesDetails : Fragment() {
    private var imageViewPath2: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageViewPath2 = view.findViewById<View>(R.id.imageViewPath2).apply {
            setOnClickListener {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.frameLayoutContainer, FragmentMoviesList())
                    ?.addToBackStack(MainActivity.FRAGMENT_TAG_MOVIES_LIST)
                    ?.commit()
            }
        }
    }


}
