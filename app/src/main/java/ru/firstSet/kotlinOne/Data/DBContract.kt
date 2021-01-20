package ru.firstSet.kotlinOne.Data

import android.provider.BaseColumns
import kotlinx.serialization.SerialName
import ru.firstSet.kotlinOne.Genre

object DBContract {
    const val DATABASE_NAME = "moviesDB.db"
    public object MovieColumns {
        const val TABLE_NAME = "moviesTable"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_POSTERPICTURE = "posterPicture"
        const val COLUMN_NAME_BACKDROPPICTURE = "backdropPicture"
        const val COLUMN_NAME_RUNTIME = "runtime"
        const val COLUMN_NAME_RATINGS = "ratings"
        const val COLUMN_NAME_OVERVIEW = "overview"
        const val COLUMN_NAME_ADULT = "adult"
        const val COLUMN_NAME_VOTE_COUNT = "vote_count"

        const val TABLE_NAME_ACTOR = "actorTable"
        const val COLUMN_NAME_ACTOR_ID = "id"
        const val COLUMN_NAME_ACTOR_PICTURE = "picture"
        const val COLUMN_NAME_ACTOR_NAME = "name"

        const val TABLE_NAME_GENRE = "genreTable"
        const val COLUMN_NAME_GENRE_ID = "id"
        const val COLUMN_NAME_GENRE_NAME = "name"
    }
}