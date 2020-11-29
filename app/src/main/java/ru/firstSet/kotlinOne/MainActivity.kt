package ru.firstSet.kotlinOne

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.frameLayoutContainer, FragmentMoviesList())
            .addToBackStack(FRAGMENT_TAG_MOVIES_LIST)
            .commit()
    }

    companion object {
        const val FRAGMENT_TAG_MOVIES_LIST = "MoviesList"
        const val FRAGMENT_TAG_MOVIES_DETAILS = "MoviesDetails"
    }
}
