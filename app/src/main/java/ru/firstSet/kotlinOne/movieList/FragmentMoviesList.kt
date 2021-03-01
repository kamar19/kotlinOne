package ru.firstSet.kotlinOne.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.viewpager.widget.PagerTabStrip
import androidx.viewpager.widget.ViewPager
import com.google.android.material.transition.Hold
import ru.firstSet.kotlinOne.R
import ru.firstSet.kotlinOne.movieDetails.FragmentMovieDetails.Companion.DURATION

class FragmentMoviesList() : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        postponeEnterTransition()
        view?.doOnPreDraw {
            startPostponedEnterTransition()
        }
        exitTransition = Hold().apply {
            duration = DURATION
            interpolator = FastOutSlowInInterpolator()
        }
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
        viewPager.currentItem = 0
        val pagerTabStrip: PagerTabStrip = view.findViewById(R.id.pagerTabStrip)
        pagerTabStrip.drawFullUnderline = false
        pagerTabStrip.setTabIndicatorColorResource(R.color.colorLinePager)
    }
}
