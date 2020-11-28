package ru.firstSet.kotlinOne

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class FragmentMoviesList() : Fragment() {
    private var someFragmentClickListener: SomeFragmentClickListener? = null
    private var fmlCombinedShape: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fmlCombinedShape = view.findViewById<View>(R.id.fmlCombinedShape).apply {
            setOnClickListener { someFragmentClickListener?.onChangeFragment() }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SomeFragmentClickListener)
            someFragmentClickListener = context
    }

    override fun onDetach() {
        super.onDetach()
        someFragmentClickListener = null
    }

    fun setListener(l: SomeFragmentClickListener) {
        someFragmentClickListener = l
    }

    interface SomeFragmentClickListener {
        fun onChangeFragment()
    }

}



