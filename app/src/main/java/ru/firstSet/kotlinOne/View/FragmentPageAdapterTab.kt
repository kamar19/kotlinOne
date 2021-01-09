package ru.firstSet.kotlinOne.View

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.firstSet.kotlinOne.Data.SeachMovie

class FragmentPageAdapterTab(val childFragmentManager: FragmentManager) : FragmentPagerAdapter(
    childFragmentManager,
    FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    override fun getItem(position: Int): FragmentMoviesListPage =
        when (position) {
            0 -> FragmentMoviesListPage(SeachMovie.MovieNowPlaying)
            1 -> FragmentMoviesListPage(SeachMovie.MovieNowPlaying)
            2 -> FragmentMoviesListPage(SeachMovie.MoviePopular)
            3 -> FragmentMoviesListPage(SeachMovie.MovieTopRated)
            else -> FragmentMoviesListPage(SeachMovie.MovieUpComing)
        }

    override fun getCount(): Int {
        return 4;
    }

    override fun getPageTitle(position: Int): CharSequence {
        return SeachMovie.values()[position].text;
    }
}

