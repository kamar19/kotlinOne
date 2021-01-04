package ru.firstSet.kotlinOne

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class Genre(val id: Int, val name: String) : Parcelable