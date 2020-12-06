package ru.firstSet.kotlinOne.DataSource

import ru.firstSet.kotlinOne.Data.Movie
import ru.firstSet.kotlinOne.R

class MoviesDataSource {
    fun getMoviesList(): List<Movie> {
        return listOf(
            Movie(
                R.drawable.avengers_end_game,
                "Avengers: End Game",
                "13+",
                false,
                "137 min",
                4,
                "Action, Adventure, Drama",
                "125 Reviews"
            ),
            Movie(
                R.drawable.tenet,
                "Tenet",
                "16+",
                true,
                "97 min",
                5,
                "Action, Sci-Fi, Thriller",
                "98 Reviews"
            ),
            Movie(
                R.drawable.black_widow,
                "Black Widow",
                "13+",
                false,
                "102 min",
                4,
                "Action, Adventure, Sci-Fi",
                "38 Reviews"
            ),
            Movie(
                R.drawable.wonder_woman_1984,
                "Wonder Woman 1984",
                "13+",
                false,
                "120 min",
                5,
                "Action, Adventure, Fantasy",
                "74 Reviews"
            ),
            Movie(
                R.drawable.avengers_end_game,
                "Avengers: End Game",
                "13+",
                false,
                "137 min",
                4,
                "Action, Adventure, Drama",
                "125 Reviews"
            ),
            Movie(
                R.drawable.tenet,
                "Tenet",
                "16+",
                true,
                "97 min",
                5,
                "Action, Sci-Fi, Thriller",
                "98 Reviews"
            ),
            Movie(
                R.drawable.black_widow,
                "Black Widow",
                "13+",
                false,
                "102 min",
                4,
                "Action, Adventure, Sci-Fi",
                "38 Reviews"
            ),
            Movie(
                R.drawable.wonder_woman_1984,
                "Wonder Woman 1984",
                "13+",
                false,
                "120 min",
                5,
                "Action, Adventure, Fantasy",
                "74 Reviews"
            ),
            Movie(
                R.drawable.avengers_end_game,
                "Avengers: End Game",
                "13+",
                false,
                "137 min",
                4,
                "Action, Adventure, Drama",
                "125 Reviews"
            ),
            Movie(
                R.drawable.tenet,
                "Tenet",
                "16+",
                true,
                "97 min",
                5,
                "Action, Sci-Fi, Thriller",
                "98 Reviews"
            ),
            Movie(
                R.drawable.black_widow,
                "Black Widow",
                "13+",
                false,
                "102 min",
                4,
                "Action, Adventure, Sci-Fi",
                "38 Reviews"
            ),
            Movie(
                R.drawable.wonder_woman_1984,
                "Wonder Woman 1984",
                "13+",
                false,
                "120 min",
                5,
                "Action, Adventure, Fantasy",
                "74 Reviews"
            )
        )
    }


}