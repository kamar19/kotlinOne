package ru.firstSet.kotlinOne

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MainActivity : AppCompatActivity(),SomeFragmentClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.frameLayoutContainer, FragmentMoviesList() )
            .add(R.id.frameLayoutContainer, FragmentMoviesDetails() )
            .hide(FragmentMoviesDetails())
            .show(FragmentMoviesList())
            .commit()
    }

    override fun onChangeFragment() {
        supportFragmentManager .beginTransaction()
            .hide(FragmentMoviesList())
            .show(FragmentMoviesDetails())
            .commit()
    }
}