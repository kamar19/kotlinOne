package ru.firstSet.kotlinOne.Data

object DBContract {
    const val DATABASE_NAME = "moviesDB2.db"
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
        const val COLUMN_NAME_GENREIDS = "genres"
        const val COLUMN_NAME_ACTORS = "actors"

        const val TABLE_NAME_ACTOR = "actorTable"
        const val COLUMN_NAME_ACTOR_MOVIEID = "actorMovieId"
        const val COLUMN_NAME_ACTOR_ID = "actorId"
        const val COLUMN_NAME_ACTOR_PICTURE = "picture"
        const val COLUMN_NAME_ACTOR_NAME = "actorName"

        const val TABLE_NAME_GENRE = "genreTable"
        const val COLUMN_NAME_GENRE_MOVIEID = "genreMovieId"
        const val COLUMN_NAME_GENRE_ID = "genreId"
        const val COLUMN_NAME_GENRE_NAME = "genreName"
    }
}