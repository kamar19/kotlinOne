package ru.firstSet.kotlinOne.utils

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import ru.firstSet.kotlinOne.R

class PickerDialog(val context: Context) {
    private var yearPlan: Int = 2021
    private var monthPlan: Int = 2
    private var dayPlan: Int = 15

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
        Log.v("datePicker", "$yearPlan $monthPlan $dayPlan ")
    }

    private fun createEventCalendar() {
        Log.v("datePicker2", "$yearPlan $monthPlan $dayPlan ")
        val text:String =  context.getString(R.string.calendar_event_is_ready) + yearPlan + ", " + monthPlan +", "+ dayPlan
        val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.show()
    }

}