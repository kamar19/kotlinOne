package ru.firstSet.kotlinOne.View

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.firstSet.kotlinOne.Data.SeachMovie as SeachMovie1

class FragmentPageAdapterTab(val childFragmentManager: FragmentManager) : FragmentPagerAdapter(
    childFragmentManager,
    FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    override fun getItem(position: Int): FragmentMoviesListPage =
        when (position) {
            0 -> FragmentMoviesListPage(SeachMovie1.MovieNowPlaying)
            1 -> FragmentMoviesListPage(SeachMovie1.MovieNowPlaying)
            2 -> FragmentMoviesListPage(SeachMovie1.MoviePopular)
            3 -> FragmentMoviesListPage(SeachMovie1.MovieTopRated)
            else -> FragmentMoviesListPage(SeachMovie1.MovieUpComing)
        }

    override fun getCount(): Int {
        return 4;
    }

    override fun getPageTitle(position: Int): CharSequence {
        return SeachMovie1.values()[position].text;
    }
}

