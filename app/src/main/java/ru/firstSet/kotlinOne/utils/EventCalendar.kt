package ru.firstSet.kotlinOne.utils

import android.app.Application
import android.content.ContentValues
import android.content.res.Resources
import android.net.Uri
import android.provider.CalendarContract
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import ru.firstSet.kotlinOne.R
import ru.firstSet.kotlinOne.data.Movie
import java.util.*

class EventCalendar(val year: Int, val month: Int, val day: Int, val movie: Movie) {
    var eventsUri = "content://calendar/movies/${movie.id}".toUri()

    val startMillis: Long = Calendar.getInstance().run {
        set(year, month, day, 19, 30)
        timeInMillis
    }
    val endMillis: Long = Calendar.getInstance().run {
        set(year, month, day, 22, 30)
        timeInMillis
    }

    fun setEvent() {
        val values = ContentValues().apply {
            put(CalendarContract.Events.DTSTART, startMillis)
            put(CalendarContract.Events.DTEND, endMillis)
            put(
                CalendarContract.Events.TITLE,
                Resources.getSystem().getString(R.string.calendar_contract_events_title)
            )
            put(
                CalendarContract.Events.DESCRIPTION,movie.title
            )
            put(CalendarContract.Events.CALENDAR_ID, calID)
            put(CalendarContract.Events.ALL_DAY, 1)
            put(CalendarContract.Events.HAS_ALARM, 1)
        }
        var url: Uri? = Application().contentResolver.insert(eventsUri, values)
    }

    companion object{
    val calID: Long = 1600

}

}