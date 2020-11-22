package ru.firstSet.kotlinOne

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView: TextView = findViewById(R.id.activityMainTextView1)
        textView.setOnClickListener{ moveToNextScreen()}
    }

    private fun moveToNextScreen() {
        val intent=Intent(this,MovieDetailsActivity::class.java)

        val transmittedString = "string to transmitted"
        val transmittedInt = 5
//        val transmittedBoolean = false

        intent.putExtra(MovieDetailsActivity.TRANSMITTEDSTRING,transmittedString)
//        intent.putExtra(MovieDetailsActivity.TRANSMITTEDBOOLEAN,transmittedBoolean)
        intent.putExtra(MovieDetailsActivity.TRANSMITTEDINT,transmittedInt)
        startActivity(intent)

//        TODO("Not yet implemented")

    }
}