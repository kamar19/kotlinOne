package ru.firstSet.kotlinOne

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FragmentMoviesDetails  : Fragment() {
    private var imageViewPath2: View? = null
    private var secondFragmentClickListener: FragmentMoviesDetails.SecondFragmentClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageViewPath2 = view.findViewById<View>(R.id.imageViewPath2).apply {
            setOnClickListener {
                secondFragmentClickListener?.onChangeSecondFragment()

            }
        }
    }
        override fun onAttach(context: Context) {
            super.onAttach(context)
            if (context is SecondFragmentClickListener)
                secondFragmentClickListener = context
        }

        override fun onDetach() {
            super.onDetach()
            secondFragmentClickListener = null
        }

        fun setListener(l: SecondFragmentClickListener) {
            secondFragmentClickListener = l
        }

        interface SecondFragmentClickListener {
            fun onChangeSecondFragment()
        }
}
