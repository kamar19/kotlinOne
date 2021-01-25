package ru.firstSet.kotlinOne

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
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
        onDelete = ForeignKey.CASCADE
    )]
)
@Parcelize
@Serializable
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

@Parcelize
@Serializable
data class GenreFromNET(val id: Int, val name: String) : Parcelable

@Serializable
data class ResultGenre(
    @SerialName("genres")
    val genres: List<GenreFromNET>
)
