package ru.firstSet.kotlinOne

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

interface SomeFragmentClickListener {
    fun onChangeFragment()
}

class FragmentMoviesList() : Fragment() {
    private var someFragmentClickListener: SomeFragmentClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)
        view?.findViewById<Button>(R.id.fml_button)?.apply {
            setOnClickListener {
                //меняем акивность
                someFragmentClickListener?.onChangeFragment()
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SomeFragmentClickListener)
            someFragmentClickListener = context
    }

    override fun onDetach() {
        super.onDetach()
        someFragmentClickListener=null
    }
}


//    var fragmentClickLestener: FragmentClickLestener



