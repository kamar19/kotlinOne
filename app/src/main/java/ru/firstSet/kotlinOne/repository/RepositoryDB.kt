package ru.firstSet.kotlinOne.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.firstSet.kotlinOne.data.*

class RepositoryDB(val movieDatabase: MovieDatabase) {
    val localDataStore: MovieDAO = movieDatabase.movieDAO

    suspend fun readMovieFromDb(idMovie: Long): Movie =
        withContext(Dispatchers.IO) {
            convertMovieRelationToMovie(listOf(localDataStore.getMovie(idMovie))).get(0)
        }

    private fun convertMovieToMovieEntity(movie: Movie, seachMovie: SeachMovie): MovieEntity {
        return MovieEntity(
            movie.id,
            movie.title,
            movie.posterPicture,
            movie.backdropPicture,
            movie.runtime,
            movie.ratings,
            movie.overview,
            movie.adult,
            movie.vote_count,
            seachMovie.seachMovie
        )
    }

    fun convertMovieRelationToMovie(movieRelation: List<MovieRelation>): List<Movie> {
        val newMovie: MutableList<Movie> = mutableListOf()
        for (moviesRelation in movieRelation) {
            val movie: Movie = Movie(
                id = moviesRelation.movie.id,
                title = moviesRelation.movie.title,
                posterPicture = moviesRelation.movie.posterPicture,
                backdropPicture = moviesRelation.movie.backdropPicture,
                runtime = moviesRelation.movie.runtime,
                ratings = moviesRelation.movie.ratings,
                overview = moviesRelation.movie.overview,
                adult = moviesRelation.movie.adult,
                vote_count = moviesRelation.movie.vote_count,
                genres = moviesRelation.genreList,
                actors = moviesRelation.actorList
            )
            newMovie.add(movie)
        }
        return newMovie
    }

    suspend fun saveMoviesToDB(movies: List<Movie>, seachMovie: SeachMovie) {
        withContext(Dispatchers.IO) {
            movies.forEach {
                val movieEntity: MovieEntity = convertMovieToMovieEntity(it, seachMovie)
                localDataStore.saveMovieAndRelation(movieEntity, it.genres, it.actors)
            }
        }
            Log.v("endSaveMovies", "${movies.size}")

    }
    fun getCountMoviesSeach(seachMovie: SeachMovie):Long =
            localDataStore.getCountMoviesSeach(seachMovie.seachMovie)
}

