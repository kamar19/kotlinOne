package ru.firstSet.kotlinOne.Data

import androidx.room.*

@Entity(
    tableName = DBContract.MovieColumns.TABLE_NAME,
    indices = [Index(DBContract.MovieColumns.COLUMN_NAME_ID)]
)
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ID)
    val id: Long = 0,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_TITLE)
    val title: String,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_POSTERPICTURE)
    var posterPicture: String,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_BACKDROPPICTURE)
    var backdropPicture: String,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_RUNTIME)
    var runtime: Int,
//        @ColumnInfo(name = MovieDBContract.MovieColumns.COLUMN_NAME_GENREIDS)
//        var genreIds: List<Genre>,
//        @ColumnInfo(name = MovieDBContract.MovieColumns.COLUMN_NAME_ACTORS)
//        var actors: List<Actor>,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_RATINGS)
    var ratings: Float,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_OVERVIEW)
    var overview: String,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ADULT)
    var adult: Int,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_VOTE_COUNT)
    var vote_count: Int

)

@Entity(
    tableName = DBContract.MovieColumns.TABLE_NAME_ACTOR,
    foreignKeys = [ForeignKey(
        entity = Movie::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("movieId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class ActorEntity(
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ACTOR_ID)
    val id: Int,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ACTOR_PICTURE)
    val picture: String,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ACTOR_NAME)
    val name: String,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ID)
    val movieId: Int,
)

@Entity(
    tableName = DBContract.MovieColumns.TABLE_NAME_GENRE,
    foreignKeys = [ForeignKey(
        entity = Movie::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("movieId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class GenreEntity(
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_GENRE_ID)
    val id: Int,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_GENRE_NAME)
    val name: String,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ID)
    val movieId: Int
)
