package ru.firstSet.kotlinOne.Data

import ru.firstSet.kotlinOne.Genre

data class Movie(
    val id: Int,
    var title: String,
    val overview: String,
    val poster: String,
    val backdrop: String,
    val ratings: Float,
    val adult: Boolean,
    val runtime: Int,
    val genres: List<Genre>,
    val actors: List<Actor>,
    val votCount: Int,
    val minAge: Int
//    val tag: String,
//    val someId: String,
)

//val id: Int,
//val title: String,
//val overview: String,
//val poster: String,
//val backdrop: String,
//val ratings: Float,
//val adult: Boolean,
//val runtime: Int,
//val genres: List<Genre>,
//val actors: List<ActorOrig>
