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
    var yearPlan: Int
    var monthPlan: Int
    var dayPlan: Int
    init {
        val currentDate: Calendar = Calendar.getInstance()
        currentDate.add(Calendar.DAY_OF_MONTH,1)
        yearPlan = currentDate.get(Calendar.YEAR)
        monthPlan = currentDate.get(Calendar.MONTH)
        dayPlan = currentDate.get(Calendar.DAY_OF_MONTH)
    }

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
        val startMillis: Long = Calendar.getInstance().run {
            set(yearPlan, monthPlan, dayPlan, hourPlan, minutePlan)
            timeInMillis
        }
        val intent = Intent(Intent.ACTION_INSERT)
            .setData(eventsUri)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
            .putExtra(CalendarContract.Events.TITLE, context.getString(R.string.calendar_contract_events_title)+" - "+ movie.title)
            .putExtra(CalendarContract.Events.DESCRIPTION, context.getString(R.string.calendar_contract_events_description))
            .putExtra(
                CalendarContract.Events.AVAILABILITY,
                CalendarContract.Events.AVAILABILITY_BUSY
            )
        startActivity(context, intent, savedInstanceState)
    }
    companion object{
        val eventsUri = "content://com.android.calendar/events".toUri()
        val hourPlan:Int = 19
        val minutePlan:Int = 30
    }
}