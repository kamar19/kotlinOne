package ru.firstSet.kotlinOne.View

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.firstSet.kotlinOne.Data.SeachMovie as SeachMovie1

class FragmentPageAdapterTab(val childFragmentManager: FragmentManager) : FragmentPagerAdapter(
    childFragmentManager,
    FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    public override fun getItem(position: Int): FragmentMoviesListPage =

//        var fmp:FragmentMoviesListPage
        when (position) {
            0 ->
                FragmentMoviesListPage(SeachMovie1.MovieNowPlaying)
            1 -> {
                FragmentMoviesListPage(SeachMovie1.MovieNowPlaying)
            }
            2 -> {
                FragmentMoviesListPage(SeachMovie1.MoviePopular)
            }
            3 -> {
                FragmentMoviesListPage(SeachMovie1.MovieTopRated)
            }
            else -> FragmentMoviesListPage(SeachMovie1.MovieUpComing)
        }
//        childFragmentManager.beginTransaction()
//            .replace(
//                R.id.viewpager,
//                fmp,
//               "childFragmentManager"
//            )
//            .commit()
//        return fmp



    public override fun getCount(): Int {
        return 4;
    }


    public override fun getPageTitle(position: Int): CharSequence {
        return SeachMovie1.values()[position].text;
    }

}

