package ru.firstSet.kotlinOne.repository

import kotlinx.coroutines.*
import ru.firstSet.kotlinOne.Data.RemoteDataStore
import ru.firstSet.kotlinOne.Data.RemoteDataStore.Companion.BASE_URL_MOVIES
import ru.firstSet.kotlinOne.Genre
import ru.firstSet.kotlinOne.data.*
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RepositoryNet(val remoteDataStore: RemoteDataStore) {

    suspend fun loadGenreFromNET(idMovie: Long): List<Genre> =
        remoteDataStore.getGenreFromNet().genres

    suspend fun loadRuntimesFromNET(id: Long): Int = withContext(Dispatchers.IO) {
        remoteDataStore.getSearchRuntimes(id).runtime
    }

    suspend fun loadActorFromNET(movieId: Long): List<Actor> {
        val actors: List<Actor>
        withContext(Dispatchers.IO) {
            actors = remoteDataStore.getSearchActor(movieId).actors.filter { it.picture != null }
            actors.forEach()
            {
                if (it.picture != null) {
                    it.picture = BASE_URL_MOVIES.plus(it.picture)
                }
            }
        }
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
            val genresMap = genres.associateBy { it.genreId }
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
}
