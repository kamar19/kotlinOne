package ru.firstSet.kotlinOne.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.firstSet.kotlinOne.Genre
import ru.firstSet.kotlinOne.data.*

class RepositoryDB (val movieDatabase: MovieDatabase){
    val localDataStore:MovieDAO = movieDatabase.movieDAO

    suspend fun readActorsFromDb(idActor: List<Actor>): List<Actor> {
        val newActors: MutableList<Actor> = mutableListOf()
        withContext(Dispatchers.IO) {
            idActor.forEach() {
                newActors.add(localDataStore.getActor(it.actorId))
            }
        }
        return newActors
    }

    suspend fun readMoviesFromDb(seachMovie: SeachMovie): List<Movie> =
        withContext(Dispatchers.IO) {
            convertMovieRelationToMovie(localDataStore.getMoviesSeach(seachMovie.seachMovie))
        }

    suspend fun readMovieFromDb(idMovie: Long): Movie =
        withContext(Dispatchers.IO) {
            convertMovieRelationToMovie(listOf(localDataStore.getMovie(idMovie))).get(0)
        }

    private fun convertMovieToMovieEntity(
        movieList: List<Movie>,
        seachMovie: SeachMovie
    ): List<MovieEntity> {
        val newMovie: MutableList<MovieEntity> = mutableListOf()
        movieList.forEach() {
            val movie: MovieEntity = MovieEntity(
                id = it.id,
                title = it.title,
                posterPicture = it.posterPicture,
                backdropPicture = it.backdropPicture,
                runtime = it.runtime,
                ratings = it.ratings,
                overview = it.overview,
                adult = it.adult,
                vote_count = it.vote_count,
                seachMovie = seachMovie.seachMovie
            )
            newMovie.add(movie)
        }
        return newMovie
    }

    private suspend fun convertMovieRelationToMovie(movieRelation: List<MovieRelation>): List<Movie> {
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
                actors = readActorsFromDb(moviesRelation.actorList)
            )
            newMovie.add(movie)
        }
        return newMovie
    }

    suspend fun saveMovieToDB(movies: List<Movie>, seachMovie: SeachMovie) {
        withContext(Dispatchers.IO) {
            localDataStore.saveMovies(convertMovieToMovieEntity(movies, seachMovie))
            movies.forEach() {
                localDataStore.upsertGenres(saveMoviesId(it.id, it.genres))
                localDataStore.saveActors(saveActorsId(it.id, it.actors))
            }
        }
    }

    fun saveMoviesId(idMovie: Long, genres: List<Genre>): List<Genre> {
        genres.forEach() {
            it.genreMovieId = idMovie
        }
        return genres
    }

    fun saveActorsId(idMovie: Long, actors: List<Actor>): List<Actor> {
        actors.forEach() {
            it.actorMovieId = idMovie
        }
        return actors
    }

}