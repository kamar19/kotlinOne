package ru.firstSet.kotlinOne

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MainActivity : AppCompatActivity(), FragmentMoviesList.SomeFragmentClickListener {
    private val rootFragment = FragmentMoviesList().apply { setListener(this@MainActivity) }
    private val secondFragment = FragmentMoviesDetails()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.frameLayoutContainer, rootFragment )
            .commit()
    }

    override fun onChangeFragment() {
        supportFragmentManager .beginTransaction()
            .add(R.id.frameLayoutContainer, secondFragment)
            .commit()
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