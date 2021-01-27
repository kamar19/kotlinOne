package ru.firstSet.kotlinOne.repository

import kotlinx.coroutines.*
import ru.firstSet.kotlinOne.GenreEntity
import ru.firstSet.kotlinOne.data.*
import ru.firstSet.kotlinOne.repository.RemoteDataStore.Companion.BASE_URL_MOVIES
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RepositoryData(val remoteDataStore: RemoteDataStore, val movieDataBase: MovieDatabase) {

    suspend fun loadGenreFromNET(idMovie: Long): List<GenreEntity> =
        remoteDataStore.getGenreFromNet().genres.map {
            GenreEntity(
                idGenre = it.id,
                name = it.name,
                genreMovieId = idMovie
            )
        }

    suspend fun loadRuntimesFromNET(id: Long): Int = withContext(Dispatchers.IO) {
        remoteDataStore.getSearchRuntimes(id).runtime
    }

    suspend fun loadActorFromNET(movieId: Long): List<ActorEntity> = withContext(Dispatchers.IO) {
        return@withContext remoteDataStore.getSearchActor(movieId).actor2.map {
            ActorEntity(
                actorId = it.id,
                actorName = it.name,
                picture = BASE_URL_MOVIES + it.profile_path,
                actorMovieId = movieId
            )
        }
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
            val genres: List<GenreEntity> = loadGenreFromNET(movie2.id)
            val genresMap = genres.associateBy { it.idGenre }
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
                actors = listOf(),
                adult = if (movie2.adult) 16 else 13,
            )
        }
    }

    suspend fun readActorFromDb(idMovie: Long): List<ActorEntity> =
        withContext(Dispatchers.IO) {
            movieDataBase.movieDAO.getActorFromSQL(idMovie)
        }


    suspend fun readMoviesFromDb(seachMovie: SeachMovie): List<Movie> =
        withContext(Dispatchers.IO) {
            val moviesEntity: List<MovieEntity> =
                movieDataBase.movieDAO.getAllMovies(seachMovie.seachMovie)
            convertMovieEntityToMovie(moviesEntity)
        }

    suspend fun readMovieFromDb(idMovie: Long): Movie =
        withContext(Dispatchers.IO) {
            val moviesEntity: MovieEntity =
                movieDataBase.movieDAO.getMovie(idMovie)
            convertMovieEntityToMovie(listOf(moviesEntity)).get(0)
        }


    suspend fun readGenreFromDb(idMovie: Long): List<GenreEntity> =
        withContext(Dispatchers.IO) {
            movieDataBase.movieDAO.getGenresFromSQL(idMovie)
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

    private suspend fun convertMovieEntityToMovie(movieEntity: List<MovieEntity>): List<Movie> {
        val newMovie: MutableList<Movie> = mutableListOf()
        for (movies in movieEntity) {
            val movie: Movie = Movie(
                id = movies.id,
                title = movies.title,
                posterPicture = movies.posterPicture,
                backdropPicture = movies.backdropPicture,
                runtime = movies.runtime,
                ratings = movies.ratings,
                overview = movies.overview,
                adult = movies.adult,
                vote_count = movies.vote_count,
                genres = readGenreFromDb(movies.id),
                actors = listOf()
            )
            newMovie.add(movie)
        }
        return newMovie
    }

    suspend fun saveMovieToDB(movies: List<Movie>, seachMovie: SeachMovie) {
        withContext(Dispatchers.IO) {
            movieDataBase.movieDAO.saveMovies(convertMovieToMovieEntity(movies, seachMovie))
            movies.forEach() {
                movieDataBase.movieDAO.saveGenres(it.genres)
            }
        }
    }

    suspend fun saveActorToDB(actors: List<ActorEntity>) {
        withContext(Dispatchers.IO) {
            movieDataBase.movieDAO.saveActors(actors)
        }
    }
}