package ru.firstSet.kotlinOne.worker

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class RepositorySP(context: Context) {

    private var movieSharedPreferences: SharedPreferences = context.getSharedPreferences(
        SHARED_PREFERENCES, Context.MODE_PRIVATE
    )
    private lateinit var editorSharedPreferences: SharedPreferences.Editor

    fun saveCurrentDate() {
        val date: String = RepositorySP.sdf.format(
            Calendar.getInstance().time
        )
        editorSharedPreferences = movieSharedPreferences.edit()
        editorSharedPreferences.putString(KEY_DATE_UPDATE, date)
        editorSharedPreferences.apply()
        Log.v("saveCurrentDate", "${date}")
    }

    fun getValue(): String? {
        return movieSharedPreferences.getString(KEY_DATE_UPDATE, "")
    }

    fun compareSavedDate(): Long {
        var hours: Long = 10
        val dateSavedString: String? = getValue()
        if (dateSavedString != null) {
            val dateSavedCalendar = Calendar.getInstance()
            dateSavedCalendar.time = sdf.parse(dateSavedString)
            hours =
                (((Calendar.getInstance().getTimeInMillis() - dateSavedCalendar.getTimeInMillis()) / 1000) / 60) / 60
            Log.v("compareSaved()", "$hours")
        }
        return hours
    }

    companion object {
        const val SHARED_PREFERENCES = "MoviesPreferences"
        const val KEY_DATE_UPDATE = "DataTimeUpdate"
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.ENGLISH)
    }

}