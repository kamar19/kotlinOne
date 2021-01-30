package ru.firstSet.kotlinOne.repository

import android.util.Log
import kotlinx.coroutines.*
import ru.firstSet.kotlinOne.GenreEntity
import ru.firstSet.kotlinOne.Genre
import ru.firstSet.kotlinOne.data.*
import ru.firstSet.kotlinOne.repository.RemoteDataStore.Companion.BASE_URL_MOVIES
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RepositoryData(val remoteDataStore: RemoteDataStore, val movieDataBase: MovieDatabase) {

    suspend fun loadGenreFromNET(idMovie: Long): List<Genre> =
        remoteDataStore.getGenreFromNet().genres

    suspend fun loadRuntimesFromNET(id: Long): Int = withContext(Dispatchers.IO) {
        remoteDataStore.getSearchRuntimes(id).runtime
    }

    suspend fun loadActorFromNET(movieId: Long): List<Actor> {
        val actors: List<Actor>
        withContext(Dispatchers.IO) {
             actors = remoteDataStore.getSearchActor(movieId).actors
            actors.forEach()
            { it.profile_path = BASE_URL_MOVIES.plus(it.profile_path) }
        }
        Log.v("loadActorFromNET", "${actors.size}")

        return actors
    }

    suspend fun loadMovieFromNET(movieId: Long): Movie {
        var movie: Movie
        withContext(Dispatchers.IO) {
            remoteDataStore.getMovie(movieId).apply {
                movie = Movie(
                    id, title, BASE_URL_MOVIES + posterPicture, BASE_URL_MOVIES + backdropPicture,
                    loadRuntimesFromNET(id), loadGenreFromNET(id), actors = listOf(),
                    vote_average, overview, adult = if (adult) 16 else 13, vote_count
                )
            }
        }
        return movie
    }

    suspend fun loadMoviesFromNET(seachMovie: String): List<Movie> = withContext(Dispatchers.IO) {
        return@withContext remoteDataStore.getMovies(seachMovie).movieForNETS.map { movie2 ->
            val genres: List<Genre> = loadGenreFromNET(movie2.id)
            val genresMap = genres.associateBy { it.id }
            Movie(
                id = movie2.id,
                title = movie2.title,
                posterPicture = BASE_URL_MOVIES + movie2.posterPicture,
                backdropPicture = BASE_URL_MOVIES + movie2.backdropPicture,
                runtime = loadRuntimesFromNET(movie2.id),
                ratings = movie2.vote_average,
                overview = movie2.overview,
                vote_count = movie2.vote_count,
                genres = movie2.genreIds.map {
                    genresMap[it] ?: throw IllegalArgumentException("Genre not found")
                },
                actors = loadActorFromNET(movie2.id),
                adult = if (movie2.adult) 16 else 13,
            )
        }
    }

//    suspend fun readActorFromDb(idMovie: Long): List<ActorEntity> =
//        withContext(Dispatchers.IO) {
//            movieDataBase.movieDAO.getActorFromSQL(idMovie)
//        }


    suspend fun readMoviesFromDb(seachMovie: SeachMovie): List<Movie> =
        withContext(Dispatchers.IO) {
            convertMovieRelationToMovie(movieDataBase.movieDAO.getMoviesSeach(seachMovie.seachMovie))
        }

    suspend fun readMovieFromDb(idMovie: Long): Movie =
        withContext(Dispatchers.IO) {
            convertMovieRelationToMovie(listOf(movieDataBase.movieDAO.getMovie(idMovie))).get(0)
        }

//    suspend fun readGenreFromDb(idMovie: Long): List<GenreEntity> =
//        withContext(Dispatchers.IO) {
//            movieDataBase.movieDAO.getGenresFromSQL(idMovie)
//        }

//    suspend fun readAllGenre(): List<GenreEntity> =
//        withContext(Dispatchers.IO) {
//            movieDataBase.movieDAO.getAllGenre()
//    }


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
        Log.v("movieRelation.size", "${movieRelation.size}")

        if (newMovie.size > 0) {
            Log.v("genreList.size", "${movieRelation.get(0).genreList.size}")
            Log.v("actorList.size", "${movieRelation.get(0).actorList.size}")
            Log.v("genreList.size", "${movieRelation.get(1).genreList.size}")
            Log.v("actorList.size", "${movieRelation.get(1).actorList.size}")
        }
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
                genres = moviesRelation.genreList.map { Genre(it.genreId, it.name) },
                actors = moviesRelation.actorList.map {
                    Actor(
                        it.actorId,
                        it.actorName,
                        it.picture
                    )
                },
            )
            Log.v("Relation.genres", "${movie.genres.size}")
            Log.v("Relation.actors", "${movie.actors.size}")
//            readGenreFromDb(moviesRelation.movie.id)

            newMovie.add(movie)

        }
        return newMovie
    }

    private suspend fun convertGenreToGenreEntity(
        genres: List<Genre>,
        movieId: Long
    ): List<GenreEntity> {
        val newGenres: MutableList<GenreEntity> = mutableListOf()
        Log.v("genres.size", "${genres.size}")
        if (genres.size > 0) {
            Log.v("name", "${genres.get(0).name}")
            Log.v(".id", "${genres.get(0).id}")
        }
        for (genre in genres) {
            val newGenre: GenreEntity = GenreEntity(
                genreId = genre.id,
                name = genre.name,
                genreMovieId = movieId
            )
            Log.v("genres.size", "${genres.size}")
            newGenres.add(newGenre)
        }
        return newGenres
    }

    private suspend fun convertActorToActorEntity(
        actors: List<Actor>,
        movieId: Long
    ): List<ActorEntity> {
        val newActors: MutableList<ActorEntity> = mutableListOf()
        Log.v("actor.size", "${actors.size}")
        for (actor in actors) {
            if (actors.size > 0) {
                Log.v("name", "${actor.id}")
                Log.v(".id", "${actor.name}")
                newActors.add(
                    ActorEntity(
                        actorId = actor.id,
                        actorName = actor.name,
                        picture = actor.profile_path.let { "" },
                        actorMovieId = movieId
                    )
                )
            }
        }
        return newActors
    }

    suspend fun saveMovieToDB(movies: List<Movie>, seachMovie: SeachMovie) {
        withContext(Dispatchers.IO) {
            movieDataBase.movieDAO.saveMovies(convertMovieToMovieEntity(movies, seachMovie))
//            Log.v("movies.size", "${movies.size}")
//            movies.forEach() {
//                Log.v("it.genres.size", "${it.genres.size}")
//
//                it.genres.forEach() {
//                    Log.v("genres.genreId", "${it.id}")
//                    Log.v("genres.name", "${it.name}")
//                }
//            }

            movies.forEach() {
                movieDataBase.movieDAO.upsertGenres(convertGenreToGenreEntity(it.genres, it.id))
                movieDataBase.movieDAO.saveActors(convertActorToActorEntity(it.actors, it.id))

                Log.v("saveActors", "${it.actors.size}")

            }


        }
    }

    suspend fun saveActorToDB(actors: List<ActorEntity>) {
        withContext(Dispatchers.IO) {
            movieDataBase.movieDAO.saveActors(actors)
        }
    }

}