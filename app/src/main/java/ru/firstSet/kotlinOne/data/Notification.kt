package ru.firstSet.kotlinOne.data

import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.Context
import androidx.core.app.*
import ru.firstSet.kotlinOne.R

interface Notifications {
    fun initialize()
    fun showNotification(movie: Movie)
    fun dismissNotification(movieId: Long)
}

class MovieNotifications(private val context: Context) : Notifications {

    init {
        initialize()
    }

    private val notificationManagerCompat: NotificationManagerCompat =
        NotificationManagerCompat.from(context)


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

        val builder = NotificationCompat.Builder(context, CHANNEL_NEW_MESSAGES)
            .setContentTitle(context.getString(R.string.load_new_movies))
            .setContentText(movie.title)
            .setSmallIcon(R.drawable.ic_message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOnlyAlertOnce(true)
            .setWhen(1)
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

