package ru.firstSet.kotlinOne

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView: TextView = findViewById(R.id.activityMainCenterTextView)
        textView.setOnClickListener{ moveToNextScreen()}
    }

    private fun moveToNextScreen() {
        val intent=Intent(this,MovieDetailsActivity::class.java)
        startActivity(intent)
    }
}