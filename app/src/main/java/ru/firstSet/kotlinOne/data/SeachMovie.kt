package ru.firstSet.kotlinOne.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class SeachMovie(val seachMovie:String,val text:String) : Parcelable {
    MovieNowPlaying("now_playing","Смотрят сейчас"),
    MoviePopular("popular","Популярные"),
    MovieTopRated("top_rated","Лучшие"),
    MovieUpComing("upcoming","Ожидаемые")
}
