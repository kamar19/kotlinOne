package ru.firstSet.kotlinOne.data

import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.*
import androidx.core.net.toUri
import ru.firstSet.kotlinOne.MainActivity
import ru.firstSet.kotlinOne.R

interface Notifications {
    fun initialize()
    fun showNotification(movie: Movie)
    fun dismissNotification(movieId: Long)
}

class MovieNotifications(private val context: Context) : Notifications {
    private var notificationManagerCompat: NotificationManagerCompat

    init {
        notificationManagerCompat = NotificationManagerCompat.from(context)
        initialize()
    }

    override fun initialize() {
        if (notificationManagerCompat.getNotificationChannel(CHANNEL_NEW_MESSAGES) == null) {
            notificationManagerCompat.createNotificationChannel(
                NotificationChannelCompat.Builder(CHANNEL_NEW_MESSAGES, IMPORTANCE_HIGH)
                    .setName(context.getString(R.string.channel_new_messages))
                    .setDescription(context.getString(R.string.channel_new_messages_description))
                    .build()
            )
        }
    }

    override fun showNotification(movie: Movie) {
        val contentUri = "kotlinOne://ru.firstSet.kotlinOne/movies/${movie.id}".toUri()
        val builder = NotificationCompat.Builder(context, CHANNEL_NEW_MESSAGES)
            .setContentTitle(context.getString(R.string.find_best_movie))
            .setContentText(movie.title)
            .setSmallIcon(R.drawable.ic_message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOnlyAlertOnce(true)
            .setShowWhen(true)
            .setAutoCancel(true)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    REQUEST_CONTENT,
                    Intent(context, MainActivity::class.java)
                        .setAction(Intent.ACTION_VIEW)
                        .setData(contentUri),
                    PendingIntent.FLAG_NO_CREATE
                )
            )
        notificationManagerCompat.notify(MOVIE_TAG, movie.id.toInt(), builder.build())
    }

    override fun dismissNotification(movieId: Long) {
        notificationManagerCompat.cancel(MOVIE_TAG, movieId.toInt())
    }

    companion object {
        private const val CHANNEL_NEW_MESSAGES = "best_new_movie"
        private const val REQUEST_CONTENT = 1
        private const val MOVIE_TAG = "new_movie"
    }
}

