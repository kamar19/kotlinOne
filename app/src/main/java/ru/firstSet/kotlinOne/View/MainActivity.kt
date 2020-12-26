package ru.firstSet.kotlinOne.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.firstSet.kotlinOne.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            callFragmentMoviesList()
        } else supportFragmentManager.findFragmentByTag(FRAGMENT_TAG_MOVIES_LIST)

    }

    companion object {
        const val FRAGMENT_TAG_MOVIES_LIST = "MoviesList"
        const val FRAGMENT_TAG_MOVIES_DETAILS = "MoviesDetails"
    }


    fun callFragmentMoviesList() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutContainer, FragmentMoviesList(this), FRAGMENT_TAG_MOVIES_LIST)
            .commit()
    }




}
