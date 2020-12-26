package ru.firstSet.kotlinOne.Data

import android.os.Parcel
import android.os.Parcelable
import ru.firstSet.kotlinOne.Genre
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    var title: String?,
    val overview: String?,
    val poster: String?,
    val backdrop: String?,
    val ratings: Float,
    val adult: Boolean,
    val runtime: Int,
    val genres: List<Genre>,
    val actors: List<Actor>,
    val votCount: Int,
    val minAge: Int
): Parcelable
