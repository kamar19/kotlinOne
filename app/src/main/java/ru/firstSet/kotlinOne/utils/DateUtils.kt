package ru.firstSet.kotlinOne.utils

import java.text.SimpleDateFormat
import java.util.*

val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.ENGLISH)

fun getCurrentDateTimeString(): String {
    return sdf.format(Date())
}


