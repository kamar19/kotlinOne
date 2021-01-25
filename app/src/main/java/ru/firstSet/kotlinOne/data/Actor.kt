package ru.firstSet.kotlinOne.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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

@Serializable
data class ResultActor(
    @SerialName("id")
    val page: Int,
    @SerialName("cast")
    val actor2: List<Actor2>
)

@Serializable
data class Actor2(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("profile_path")
    val profile_path: String?
)


