package ru.firstSet.kotlinOne.Data

import android.os.Parcel
import android.os.Parcelable
import ru.firstSet.kotlinOne.Genre

//@Suppress("UNREACHABLE_CODE")
//
//data class Movie_old(
//    val id: Int,
//    var title: String?,
//    val overview: String?,
//    val poster: String?,
//    val backdrop: String?,
//    val ratings: Float,
//    val adult: Boolean,
//    val runtime: Int,
//    val genres: List<Genre>,
//    val actors: List<Actor>,
//    val votCount: Int,
//    val minAge: Int
//) : Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readInt(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readFloat(),
//        parcel.readByte() != 0.toByte(),
//        parcel.readInt(),
//        genres = TODO("genres"),
//        actors = TODO("actors"),
//        votCount = parcel.readInt(),
//        minAge = parcel.readInt()
//    )
//
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeInt(id)
//        parcel.writeString(title)
//        parcel.writeString(overview)
//        parcel.writeString(poster)
//        parcel.writeString(backdrop)
//        parcel.writeFloat(ratings)
//        parcel.writeByte(if (adult) 1 else 0)
//        parcel.writeInt(runtime)
//        parcel.writeInt(votCount)
//        parcel.writeInt(minAge)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<Movie> {
//        override fun createFromParcel(parcel: Parcel): Movie {
//            return Movie(parcel)
//        }
//
//        override fun newArray(size: Int): Array<Movie?> {
//            return arrayOfNulls(size)
//        }
//    }
//}
