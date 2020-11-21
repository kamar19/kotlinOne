package ru.firstSet.kotlinOne

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView


class MovieDetailsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val transmittedString:String? = intent.getStringExtra(TRANSMITTEDSTRING)
        val transmittedInt: Int = intent.getIntExtra(TRANSMITTEDINT,-1)

//        val textView: TextView = findViewById(R.id.movieDetailsActivityTextView1)
//        textView.setText( transmittedString)
//
    }

    companion object {
        const val TRANSMITTEDSTRING = "transmittedString"
        const val TRANSMITTEDINT = "transmittedInt"
//        const val TRANSMITTEDBOOLEAN = true
    }
}