package ru.firstSet.kotlinOne.movieList

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.firstSet.kotlinOne.data.SeachMovie

class FragmentPageAdapterTab(val childFragmentManager: FragmentManager) : FragmentPagerAdapter(
    childFragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    override fun getItem(position: Int): FragmentMoviesListPage =
        when (position) {
            0 -> FragmentMoviesListPage(SeachMovie.MovieNowPlaying)
            1 -> FragmentMoviesListPage(SeachMovie.MoviePopular)
            2 -> FragmentMoviesListPage(SeachMovie.MovieTopRated)
            3 -> FragmentMoviesListPage(SeachMovie.MovieUpComing)
            else -> FragmentMoviesListPage(SeachMovie.MovieUpComing)
        }

    override fun getCount(): Int {
        return 4;
    }

    override fun getPageTitle(position: Int): CharSequence {
        return SeachMovie.values()[position].text;
    }
}

