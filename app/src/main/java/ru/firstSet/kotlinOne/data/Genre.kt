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
        deferred = true
    )],
    primaryKeys = arrayOf("genreMovieId", "genreId")
)
@Serializable
@Parcelize
data class Genre(
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_GENRE_ID)
    @SerialName("id")
    var genreId: Int = 0,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_GENRE_MOVIEID)
    var genreMovieId: Long = 0,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_GENRE_NAME)
    @SerialName("name")
    var name: String = ""
) : Parcelable

@Serializable
data class ResultGenre(
    @SerialName("genres")
    val genres: List<Genre>
)
