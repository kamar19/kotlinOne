package ru.firstSet.kotlinOne

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.firstSet.kotlinOne.data.DBContract
import ru.firstSet.kotlinOne.data.MovieEntity

@Entity(
    tableName = DBContract.MovieColumns.TABLE_NAME_GENRE,
    foreignKeys = [ForeignKey(
        entity = MovieEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("genreMovieId"),
        onDelete = ForeignKey.CASCADE,
//        deferred = false
    )],
    indices = arrayOf(Index(value = ["genreMovieId"]))
//    primaryKeys = [ "index", "genreMovieId" ],
)
@Parcelize
@Serializable
data class GenreEntity(
    @PrimaryKey(autoGenerate = true)
//    @Ignore
    var index: Int=0,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_GENRE_ID)
    @SerialName("id")
    var genreId: Int=0,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_GENRE_MOVIEID)
    var genreMovieId: Long=0,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_GENRE_NAME)
    var name: String = ""
) : Parcelable

@Parcelize
@Serializable
data class Genre(
    val id: Int,
//    var genreMovieId: Long,
    val name: String,

    ) : Parcelable

@Serializable
data class ResultGenre(
    @SerialName("genres")
    val genres: List<Genre>
)
