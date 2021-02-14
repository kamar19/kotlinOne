package ru.firstSet.kotlinOne.utils

import ru.firstSet.kotlinOne.MainActivity
import java.text.SimpleDateFormat
import java.util.*


private val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.ENGLISH)
val timeStart = Calendar.getInstance()

fun getCurrentDateTimeString(): String {
    return sdf.format(Date())
}

fun getCurrentDateTime(): Date {
    return Date()
}

fun getMinutesPassedStart(calendar: Calendar): Long {
    return  (((Calendar.getInstance().getTimeInMillis() - calendar.getTimeInMillis()) / 1000) / 60) / 60
}
