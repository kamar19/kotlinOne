package ru.firstSet.kotlinOne.data

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.firstSet.kotlinOne.GenreEntity
import ru.firstSet.kotlinOne.Genre

@Entity(
    tableName = DBContract.MovieColumns.TABLE_NAME,
    indices = [Index(DBContract.MovieColumns.COLUMN_NAME_ID)]
)
public data class MovieEntity( // класс для работы с БД
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ID)
    var id: Long=0 ,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_TITLE)
    var title: String="",
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_POSTERPICTURE)
    var posterPicture: String="",
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_BACKDROPPICTURE)
    var backdropPicture: String="",
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_RUNTIME)
    var runtime: Int= 0,
    var ratings: Float = 0F,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_OVERVIEW)
    var overview: String="",
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_ADULT)
    var adult: Int= 0,
    @ColumnInfo(name = DBContract.MovieColumns.COLUMN_NAME_VOTE_COUNT)
    var vote_count: Int= 0,
    var seachMovie: String=""
)

data class MovieRelation(
    @Embedded val movie: MovieEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "genreMovieId",
    )
    val genreList: List<GenreEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "actorMovieId",
    )
    val actorList: List<ActorEntity>
)

@Parcelize
@Serializable
data class Movie( // Итоговый класс для работы
    var id: Long,
    var title: String,
    @SerialName("poster_path")
    var posterPicture: String,
    @SerialName("backdrop_path")
    var backdropPicture: String,
    var runtime: Int,
    @SerialName("genre_ids")
    var genres: List<Genre>,
    var actors: List<Actor>,
    @SerialName("vote_average")
    var ratings: Float,
    var overview: String,
    var adult: Int,
    var vote_count: Int
) : Parcelable

@Serializable
data class MovieForNET( // класс для работы с некоторыми запросами API
    @SerialName("id")
    val id: Long,
    @SerialName("backdrop_path")
    val backdropPicture: String,
    @SerialName("title")
    val title: String,
    @SerialName("poster_path")
    val posterPicture: String,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("vote_average")
    val vote_average: Float,
    val overview: String,
    val adult: Boolean,
    val vote_count: Int
)

@Serializable
data class MovieDetail( // класс для получения запросов Detail из API
    @SerialName("id")
    val id: Long,
    @SerialName("backdrop_path")
    val backdropPicture: String,
    @SerialName("title")
    val title: String,
    @SerialName("poster_path")
    val posterPicture: String,
    @SerialName("genres")
    val genreIds: List<Genre>,
    @SerialName("vote_average")
    val vote_average: Float,
    val overview: String,
    val adult: Boolean,
    val vote_count: Int
)

@Serializable
data class ResultMovie(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val movieForNETS: List<MovieForNET>
)

@Serializable
data class ResultDetails(
    @SerialName("id")
    val id: Int,
    @SerialName("runtime")
    val runtime: Int
)
