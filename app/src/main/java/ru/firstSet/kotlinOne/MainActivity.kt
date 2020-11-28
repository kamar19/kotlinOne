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
//        if (savedInstanceState==null){

        someFragment = rootFragment
        someFragment.apply {
            supportFragmentManager.beginTransaction()
                .add(R.id.frameLayoutContainer, rootFragment, FRAGMENT_TAG_MOVIES_LIST)
                .addToBackStack(FRAGMENT_TAG_MOVIES_LIST)
                .commit()
        }
//        } else {
//            supportFragmentManager.findFragmentByTag(FRAGMENT_TAG_MOVIES_LIST)
//
//        }


    }

    override fun onChangeFragment() {
        someFragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG_MOVIES_DETAILS)
        if (someFragment == null) {
            someFragment = secondFragment
            someFragment.apply {
                supportFragmentManager.beginTransaction()
                    .add(R.id.frameLayoutContainer, secondFragment, FRAGMENT_TAG_MOVIES_DETAILS)
                    .addToBackStack(FRAGMENT_TAG_MOVIES_DETAILS)
                    .commit()
            }

        } else {
            supportFragmentManager.beginTransaction()
                .show(secondFragment)
                .hide(rootFragment)
                .commit()
        }

    }

    companion object {
        const val FRAGMENT_TAG_MOVIES_LIST = "MoviesList"
        const val FRAGMENT_TAG_MOVIES_DETAILS = "MoviesDetails"
    }

    override fun onChangeSecondFragment() {
        someFragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG_MOVIES_LIST)
        if (someFragment == null) {
            someFragment = rootFragment
            someFragment.apply {
                supportFragmentManager.beginTransaction()
                    .add(R.id.frameLayoutContainer, rootFragment, FRAGMENT_TAG_MOVIES_LIST)
                    .addToBackStack(FRAGMENT_TAG_MOVIES_LIST)
                    .commit()
            }
        } else {
            supportFragmentManager.beginTransaction()
                .hide(secondFragment)
                .show(rootFragment)
                .commit()
        }



//        supportFragmentManager.beginTransaction()
//            .hide(secondFragment)
//            .show(rootFragment)
//            .commit()
    }
}

//class WS03SolutionActivity : AppCompatActivity(), WS03SolutionFragment.ClickListener {
//
//    private val rootFragment = WS03SolutionFragment().apply { setListener(this@WS03SolutionActivity) }
//    private val secondFragment = WS03SecondFragment()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_ws02_ws03)
//
//        supportFragmentManager.beginTransaction()
//            .apply {
//                add(R.id.persistent_container, rootFragment)
//                add(R.id.fragments_container, secondFragment)
//                commit()
//            }
//    }
//
//    override fun increaseValue() {
//        secondFragment.increaseValue()
//    }
//
//    override fun changeBackground() {
//        secondFragment.changeBackground()
//    }
//}