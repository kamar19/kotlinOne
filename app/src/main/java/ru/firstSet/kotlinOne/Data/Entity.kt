package ru.firstSet.kotlinOne.Data

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(
    tableName = DBContract.MovieColumns.TABLE_NAME,
    indices = [Index(DBContract.MovieColumns.COLUMN_NAME_ID)]
)
public data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ID)
    var id: Long = 0,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_TITLE)
    var title: String="",
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_POSTERPICTURE)
    var posterPicture: String="",
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_BACKDROPPICTURE)
    var backdropPicture: String="",
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_RUNTIME)
    var runtime: Int= 0,
//    @Ignore
//    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_GENREIDS)
//    public var genres: List<Genre> = listOf(),
    // withContext(Dispatchers.IO) { movieRepository.loadGenre(id) }
//    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ACTORS)
//    var actors: List<ActorEntity>,
//    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_RATINGS)
    var ratings: Float = 0F,

    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_OVERVIEW)
    var overview: String="",
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ADULT)
    var adult: Int= 0,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_VOTE_COUNT)
    var vote_count: Int= 0,
    var seachMovie: String=""
)

@Entity(
    tableName = DBContract.MovieColumns.TABLE_NAME_ACTOR,
    foreignKeys = [ForeignKey(
        entity = MovieEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("actorMovieId"),
        onDelete = ForeignKey.NO_ACTION
    )]
)
@Serializable
@Parcelize
data class ActorEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ACTOR_MOVIEID)
    var actorMovieId: Long,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ACTOR_ID)
    var actorId: Int,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ACTOR_PICTURE)
    var picture: String,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ACTOR_NAME)
    var actorName: String
):Parcelable

//@Entity(
//    tableName = DBContract.MovieColumns.TABLE_NAME_ACTOR,
//    foreignKeys = [ForeignKey(
//        entity = MovieEntity::class,
//        parentColumns = arrayOf("id"),
//        childColumns = arrayOf("actorMovieId"),
//        onDelete = ForeignKey.NO_ACTION
//    )]
//)
//@Serializable
//@Parcelize
//data class ActorEntity(
//    @PrimaryKey(autoGenerate = false)
//    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ACTOR_ID)
//    var actorId: Int,
//    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ACTOR_PICTURE)
//    var picture: String,
//    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ACTOR_NAME)
//    var actorName: String,
//    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ACTOR_MOVIEID)
//    var actorMovieId: Long,
//):Parcelable

@Entity(
    tableName = DBContract.MovieColumns.TABLE_NAME_GENRE,
    foreignKeys = [ForeignKey(
        entity = MovieEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("genreMovieId"),
        onDelete = ForeignKey.CASCADE
    )]
)
@Parcelize
@Serializable
//@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
data class GenreEntity(
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_GENRE_MOVIEID)
    @PrimaryKey(autoGenerate = false)
    var genreMovieId: Long,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_GENRE_ID)
    @SerialName("id")
    var idGenre: Int,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_GENRE_NAME)
    var name: String=""
): Parcelable

//data class MovieAndActorAndGenre(
//    @Embedded
//    val movieEntity: Movie,
//    @Relation(parentColumn = "id", entityColumn = "genreMovieId", entity = Genre::class)
//    var genres: List<Genre>? = null,
//    @Relation(parentColumn = "id", entityColumn = "actorMovieId", entity = ActorEntity::class)
//    var actors: List<ActorEntity>? = null

//)
