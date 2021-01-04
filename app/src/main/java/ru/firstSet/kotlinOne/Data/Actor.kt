package ru.firstSet.kotlinOne.Data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class Actor(
    val id: Int,
    val picture: String,
    val name: String
) : Parcelable
