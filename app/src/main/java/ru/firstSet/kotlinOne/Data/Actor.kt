package ru.firstSet.kotlinOne.Data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Actor(
    val id: Int,
    val picture: String,
    val name: String
) : Parcelable

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