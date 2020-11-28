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
    private var buttonChangeFragment: Button? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonChangeFragment = view.findViewById<Button>(R.id.fml_button).apply {
            setOnClickListener { someFragmentClickListener?.onChangeFragment() }
        }
//        view?.findViewById<Button>(R.id.fml_button)?.apply {
//            setOnClickListener {
//                //меняем активность
//                someFragmentClickListener?.onChangeFragment()
//            }

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
//
//class WS03SolutionFragment : Fragment() {
//
//    private var btnIncrement: Button? = null
//    private var btnChangeBackground: Button? = null
//    private var listener: ClickListener? = null
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? = inflater.inflate(R.layout.fragment_root_ws_03, container, false)
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        btnIncrement = view.findViewById<Button>(R.id.btn_increment).apply {
//            setOnClickListener { listener?.increaseValue() }
//        }
//        btnChangeBackground = view.findViewById<Button>(R.id.btn_change_background).apply {
//            setOnClickListener { listener?.changeBackground() }
//        }
//
//    }
//
//    fun setListener(l: ClickListener) {
//        listener = l
//    }
//
//    interface ClickListener {
//        fun increaseValue()
//        fun changeBackground()
//    }
//}



