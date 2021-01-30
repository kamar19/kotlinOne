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
        onDelete = ForeignKey.CASCADE
    )],
    indices = arrayOf(Index(value = ["actorMovieId"]))

)
@Serializable
@Parcelize
data class ActorEntity(
    @PrimaryKey(autoGenerate = false)
//    @Ignore
    var index: Int=0,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ACTOR_ID)
    @SerialName("id")
    var actorId: Int=0,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ACTOR_MOVIEID)
    var actorMovieId: Long=0,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ACTOR_PICTURE)
    @SerialName("profile_path")
    var picture: String="",
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ACTOR_NAME)
    @SerialName("name")
    var actorName: String=""
):Parcelable

@Serializable
data class ResultActor(
    @SerialName("id")
    val page: Int,
    @SerialName("cast")
    val actors: List<Actor>
)
@Parcelize
@Serializable
data class Actor(
    @SerialName("id")
    val id: Int=0,
    @SerialName("name")
    val name: String="",
    @SerialName("profile_path")
    var profile_path: String?=""
):Parcelable

//
//@Entity(
//    tableName = DBContract.MovieColumns.TABLE_NAME_ACTOR,
//    foreignKeys = [ForeignKey(
//        entity = MovieEntity::class,
//        parentColumns = arrayOf("id"),
//        childColumns = arrayOf("actorMovieId"),
//        onDelete = ForeignKey.CASCADE
//    )],
//)
//@Parcelize
//@Serializable
//data class ActorIdEntity(
//    @SerialName("id")
//    val id: Int=0,
//):Parcelable
//
