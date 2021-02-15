package ru.firstSet.kotlinOne.utils

import java.text.SimpleDateFormat
import java.util.*

val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.ENGLISH)

fun getCurrentDateTimeString(): String {
    return sdf.format(Date())
}

fun getMinutesPassedStart(calendar: Calendar?): Long {
    if (calendar == null) return 10
    else
        return (((Calendar.getInstance()
            .getTimeInMillis() - calendar.getTimeInMillis()) / 1000) / 60) / 60
}


