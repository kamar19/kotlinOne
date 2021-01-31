package ru.firstSet.kotlinOne.data

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(
    tableName = DBContract.MovieColumns.TABLE_NAME_ACTOR,
    foreignKeys = [ForeignKey(
        entity = MovieEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("actorMovieId"),
        onDelete = ForeignKey.CASCADE,
    )],
    primaryKeys = arrayOf("actorMovieId", "actorId")
)
@Serializable
@Parcelize
data class Actor(
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ACTOR_ID)
    @SerialName("id")
    var actorId: Int = 0,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ACTOR_MOVIEID)
    var actorMovieId: Long = 0,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ACTOR_PICTURE)
    @SerialName("profile_path")
    var picture: String? = "",
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ACTOR_NAME)
    @SerialName("name")
    var actorName: String = ""
) : Parcelable

@Serializable
data class ResultActor(
    @SerialName("id")
    val page: Int,
    @SerialName("cast")
    val actors: List<Actor>
)
