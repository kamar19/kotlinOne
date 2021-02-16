package ru.firstSet.kotlinOne.utils

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.widget.DatePicker
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import ru.firstSet.kotlinOne.R
import ru.firstSet.kotlinOne.data.Movie
import java.util.*

class EventCalendar(val context: Context, val savedInstanceState: Bundle?, val movie: Movie) {
    var yearPlan: Int = 2021
    var monthPlan: Int = 2
    var dayPlan: Int = 15

    val dateSetListener = object : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(
            view: DatePicker, year: Int, monthOfYear: Int,
            dayOfMonth: Int
        ) {
            yearPlan = year
            monthPlan = monthOfYear
            dayPlan = dayOfMonth
            createEventCalendar()
        }
    }

    fun showPickerDialog() {
        val datePickerDialog = context.let {
            DatePickerDialog(
                it, dateSetListener, yearPlan, monthPlan, dayPlan
            ).show()
        }
    }

    private fun createEventCalendar() {
        val eventsUri = "content://com.android.calendar/events".toUri()
        val startMillis: Long = Calendar.getInstance().run {
            set(yearPlan, monthPlan, dayPlan, 19, 30)
            timeInMillis
        }
        val endMillis: Long = Calendar.getInstance().run {
            set(yearPlan, monthPlan, dayPlan, 22, 30)
            timeInMillis
        }
        val intent = Intent(Intent.ACTION_INSERT)
            .setData(eventsUri)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
            .putExtra(CalendarContract.Events.TITLE, context.getString(R.string.calendar_contract_events_title)+" - "+ movie.title)
            .putExtra(CalendarContract.Events.DESCRIPTION, context.getString(R.string.calendar_contract_events_description))
            .putExtra(
                CalendarContract.Events.AVAILABILITY,
                CalendarContract.Events.AVAILABILITY_BUSY
            )
        startActivity(context, intent, savedInstanceState)
    }
}