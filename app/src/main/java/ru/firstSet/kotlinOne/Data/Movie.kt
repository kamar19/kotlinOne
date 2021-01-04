package ru.firstSet.kotlinOne.Data

import android.os.Parcel
import android.os.Parcelable
import ru.firstSet.kotlinOne.Genre
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName

@Parcelize
data class Movie(
        val id: Int,
        var title: String,
        @SerialName("poster_path")
        var posterPicture: String,
        @SerialName("backdrop_path")
        val backdropPicture: String,
        val runtime: Int,
        @SerialName("genre_ids")
        val genreIds: List<Genre>,
        var actors: List<Actor>,
        @SerialName("vote_average")
        var ratings: Float,
        var overview: String,
        val adult: Int,
        val vote_count: Int
): Parcelable
