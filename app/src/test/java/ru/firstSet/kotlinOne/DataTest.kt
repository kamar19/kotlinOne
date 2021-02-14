package ru.firstSet.kotlinOne

import ru.firstSet.kotlinOne.data.Actor
import ru.firstSet.kotlinOne.data.Movie
import ru.firstSet.kotlinOne.data.MovieEntity
import ru.firstSet.kotlinOne.data.SeachMovie

val actors: List<Actor> = listOf(
    Actor(84433, 671039, "https://image.tmdb.org/t/p/w342/h94QUkKrwUncYhJ1eMz23kBAJuc.jpg"),
    Actor(37919, 671039, "https://image.tmdb.org/t/p/w342/k5XQM1XXG71GLd41hFcx8yhkxRy.jpg"),
    Actor(1407184, 671039, "https://image.tmdb.org/t/p/w342/zHxqojtMgk5aBqMQyTqcoPc4TRG.jpg")
)

val ganres: List<Genre> = listOf(
    Genre(28, 671039, "Action"),
    Genre(53, 671039, "Thriller"),
    Genre(80, 671039, "Crime"),
    Genre(18, 671039, "Drama"),
    Genre(28, 724989, "Action"),
    Genre(53, 724989, "Thriller"),
    Genre(14, 400160, "Fantasy"),
    Genre(16, 400160, "Animation"),
    Genre(12, 400160, "Fantasy"),
    Genre(35, 400160, "Comedy"),
    Genre(10751, 400160, "Family")
)
val ganres671039: List<Genre> = listOf(
    Genre(28, 671039, "Action"),
    Genre(53, 671039, "Thriller"),
    Genre(80, 671039, "Crime"),
    Genre(18, 671039, "Drama")
)

val actors671039: List<Actor> = listOf(
    Actor(84433, 671039, "https://image.tmdb.org/t/p/w342/h94QUkKrwUncYhJ1eMz23kBAJuc.jpg"),
    Actor(37919, 671039, "https://image.tmdb.org/t/p/w342/k5XQM1XXG71GLd41hFcx8yhkxRy.jpg"),
    Actor(1407184, 671039, "https://image.tmdb.org/t/p/w342/zHxqojtMgk5aBqMQyTqcoPc4TRG.jpg")
)
val ganres724989: List<Genre> = listOf(
    Genre(28, 671039, "Action"),
    Genre(53, 671039, "Thriller")
)

val actors724989: List<Actor> = listOf(
    Actor(
        62,
        724989,
        "https://image.tmdb.org/t/p/w342/A1XBu3CffBpSK8HEIJM8q7Mn4lz.jpg",
        "Bruce Willis"
    ),
    Actor(
        57171,
        724989,
        "https://image.tmdb.org/t/p/w342/qZR4VAB2t0qawkH88Wv3K7y67nM.jpg",
        "Jesse Metcalfe"
    ),
    Actor(
        1734214,
        724989,
        "https://image.tmdb.org/t/p/w342/nS7tdSPGtmSlkMCHO2W18SgFVWP.jpg",
        "Natalie Eva Marie"
    )
)


val movieListTest: List<Movie> = listOf(
    Movie(
        671039,
        "Rogue City",
        "https://image.tmdb.org/t/p/w342/9HT9982bzgN5on1sLRmc1GMn6ZC.jpg",
        "https://image.tmdb.org/t/p/w342/gnf4Cb2rms69QbCnGFJyqwBWsxv.jpg",
        116,
        ganres671039,
        actors671039,
        6F,
        "Caught in the crosshairs of police corruption and Marseille’s warring gangs, a loyal cop must protect his squad by taking matters into his own hands.",
        13,
        200
    ),
    Movie(
        724989,
        "Hard Kill",
        "https://image.tmdb.org/t/p/w342/ugZW8ocsrfgI95pnQ7wrmKDxIe.jpg",
        "https://image.tmdb.org/t/p/w342/86L8wqGMDbwURPni2t7FQ0nDjsH.jpg",
        98,
        ganres724989,
        actors724989,
        5F,
        "The work of billionaire tech CEO Donovan Chalmers is so valuable that he hires mercenaries to protect it, and a terrorist group kidnaps his daughter just to get it.",
        13,
        151
    )
)

val movieEntityTest: List<MovieEntity> = listOf(
    MovieEntity(
        671039,
        "Rogue City",
        "https://image.tmdb.org/t/p/w342/9HT9982bzgN5on1sLRmc1GMn6ZC.jpg",
        "https://image.tmdb.org/t/p/w342/gnf4Cb2rms69QbCnGFJyqwBWsxv.jpg",
        116,
        6F,
        "Caught in the crosshairs of police corruption and Marseille’s warring gangs, a loyal cop must protect his squad by taking matters into his own hands.",
        13,
        200, SeachMovie.MovieUpComing.seachMovie
    ),
    MovieEntity(
        724989,
        "Hard Kill",
        "https://image.tmdb.org/t/p/w342/ugZW8ocsrfgI95pnQ7wrmKDxIe.jpg",
        "https://image.tmdb.org/t/p/w342/86L8wqGMDbwURPni2t7FQ0nDjsH.jpg",
        98,
        5F,
        "The work of billionaire tech CEO Donovan Chalmers is so valuable that he hires mercenaries to protect it, and a terrorist group kidnaps his daughter just to get it.",
        13,
        151, SeachMovie.MovieUpComing.seachMovie
    )

)
