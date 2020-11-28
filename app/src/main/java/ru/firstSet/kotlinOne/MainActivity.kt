package ru.firstSet.kotlinOne

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
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
//        someFragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG_MOVIES_DETAILS)
//        if (someFragment == null) {
//            someFragment = secondFragment
//            someFragment.apply {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutContainer, secondFragment)
            .commit()
//            }
//        } else {
//            supportFragmentManager.beginTransaction()
//                .show(secondFragment)
//                .commit()
//        }
//        supportFragmentManager.beginTransaction()
//            .remove(rootFragment)
//            .commit()
    }

    companion object {
        const val FRAGMENT_TAG_MOVIES_LIST = "MoviesList"
        const val FRAGMENT_TAG_MOVIES_DETAILS = "MoviesDetails"
    }

    override fun onChangeSecondFragment() {

        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutContainer, rootFragment)
            .commit()

//        someFragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG_MOVIES_LIST)
//        if (someFragment == null) {
//            someFragment = rootFragment
//            someFragment.apply {
//                supportFragmentManager.beginTransaction()
//                    .add(R.id.frameLayoutContainer, rootFragment, FRAGMENT_TAG_MOVIES_LIST)
//                    .addToBackStack(FRAGMENT_TAG_MOVIES_LIST)
//                    .commit()
//            }
//        } else {
//            supportFragmentManager.beginTransaction()
//                .show(rootFragment)
//                .commit()
//        }
//        supportFragmentManager.beginTransaction()
//            .remove(secondFragment)
//            .commit()


//        supportFragmentManager.beginTransaction()
//            .hide(secondFragment)
//            .show(rootFragment)
//            .commit()
    }

}
