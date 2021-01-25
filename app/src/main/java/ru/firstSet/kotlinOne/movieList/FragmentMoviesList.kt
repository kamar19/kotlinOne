package ru.firstSet.kotlinOne.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerTabStrip
import androidx.viewpager.widget.ViewPager
import ru.firstSet.kotlinOne.R

class FragmentMoviesList() : Fragment() {
    private var fmlConstraintLayoutList: ConstraintLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager: ViewPager = view.findViewById(R.id.viewpager)
        viewPager.adapter = FragmentPageAdapterTab(getChildFragmentManager())
        viewPager.currentItem=0
        val pagerTabStrip: PagerTabStrip = view.findViewById(R.id.pagerTabStrip)
        pagerTabStrip.drawFullUnderline=false
        pagerTabStrip.setTabIndicatorColorResource( R.color.colorLinePager )
        fmlConstraintLayoutList =
            view.findViewById<ConstraintLayout>(R.id.fmlConstraintLayoutList).apply {
                setOnClickListener {
                    // В будующем буду использовать этот лиссенер, возможно для поиска городов или стран
                    // что бы в зависимости от выбора показывались локализованные данные для региона.
                }
            }
    }
}
