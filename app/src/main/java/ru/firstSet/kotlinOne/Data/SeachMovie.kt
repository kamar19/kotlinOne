package ru.firstSet.kotlinOne.Data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class SeachMovie(val seachMovie:String,val text:String) : Parcelable {
    MovieNowPlaying("now_playing","Now playing"),
    MoviePopular("popular","Popular"),
    MovieTopRated("top_rated","Top rated"),
    MovieUpComing("upcoming","Upcoming")
}
