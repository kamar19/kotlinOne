package ru.firstSet.kotlinOne

data class Movie(
    val nameImageView: Int? = null,
    var nameMovie: String? = "",
    val someId: String? = null,
    val isLike: Boolean = false,
    val minuteTime: String? = null,
    val ratingBarRating: Int? = null,
    val tag: String? = null,
    val review: String? = null
)