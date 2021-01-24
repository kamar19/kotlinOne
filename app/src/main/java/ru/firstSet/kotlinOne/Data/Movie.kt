package ru.firstSet.kotlinOne.Data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
data class Movie(
    var id: Long,
    var title: String,
    @SerialName("poster_path")
    var posterPicture: String,
    @SerialName("backdrop_path")
    var backdropPicture: String,
    var runtime: Int,
    @SerialName("genre_ids")
    var genres: List<GenreEntity>,
    var actors: List<Actor>,
    @SerialName("vote_average")
    var ratings: Float,
    var overview: String,
    var adult: Int,
    var vote_count: Int
) : Parcelable

//@Parcelize
//data class Movie2(
//    val id: Long,
//    var title: String,
//    @SerialName("poster_path")
//    var posterPicture: String,
//    @SerialName("backdrop_path")
//    var backdropPicture: String,
//    var runtime: Int,
////    @SerialName("genre_ids")
////    var genreIds: List<Genre>,
////    var actors: List<Actor>,
//    @SerialName("vote_average")
//    var ratings: Float,
//    var overview: String,
//    var adult: Int,
//    var vote_count: Int
//) : Parcelable

@Serializable
data class MovieForNET(
    @SerialName("id")
    val id: Long,
    @SerialName("backdrop_path")
    val backdropPicture: String?,
    @SerialName("title")
    val title: String,
    @SerialName("poster_path")
    val posterPicture: String?,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
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
