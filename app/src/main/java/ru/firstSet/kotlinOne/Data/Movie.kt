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
        var backdropPicture: String,
        val runtime: Int,
        @SerialName("genre_ids")
        var genreIds: List<Genre>,
        var actors: List<Actor>,
        @SerialName("vote_average")
        var ratings: Float,
        var overview: String,
        var adult: Int,
        var vote_count: Int
): Parcelable
