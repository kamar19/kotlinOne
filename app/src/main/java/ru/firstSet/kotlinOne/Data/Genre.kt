package ru.firstSet.kotlinOne

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Genre(val id: Int, val name: String) : Parcelable

@Serializable
data class ResultGenre(
    @SerialName("genres")
    val genres: List<Genre>
)
