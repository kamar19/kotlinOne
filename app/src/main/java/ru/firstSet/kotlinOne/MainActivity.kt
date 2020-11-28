package ru.firstSet.kotlinOne

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), FragmentMoviesList.SomeFragmentClickListener,
    FragmentMoviesDetails.SecondFragmentClickListener {
    private val rootFragment = FragmentMoviesList().apply { setListener(this@MainActivity) }
    private val secondFragment = FragmentMoviesDetails()
    private var someFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        someFragment = rootFragment
        someFragment.apply {
            supportFragmentManager.beginTransaction()
                .add(R.id.frameLayoutContainer, rootFragment, FRAGMENT_TAG_MOVIES_LIST)
                .addToBackStack(FRAGMENT_TAG_MOVIES_LIST)
                .commit()
        }
    }

    override fun onChangeFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutContainer, secondFragment, FRAGMENT_TAG_MOVIES_DETAILS)
            .commit()
    }

    override fun onChangeSecondFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutContainer, rootFragment, FRAGMENT_TAG_MOVIES_LIST)
            .commit()
    }

    companion object {
        const val FRAGMENT_TAG_MOVIES_LIST = "MoviesList"
        const val FRAGMENT_TAG_MOVIES_DETAILS = "MoviesDetails"
    }
}
