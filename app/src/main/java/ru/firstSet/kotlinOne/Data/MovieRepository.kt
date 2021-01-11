package ru.firstSet.kotlinOne.Data

import kotlinx.coroutines.*
import ru.firstSet.kotlinOne.Genre
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MovieRepository {
    private val BASE_URL_MOVIES = "https://image.tmdb.org/t/p/original"
    private val remoteDataStore = ru.firstSet.kotlinOne.Data.RemoteDataStore

    var coroutineScope = CoroutineScope(Dispatchers.Main)
    suspend fun loadGenre(): List<Genre> = withContext(Dispatchers.IO) {
        remoteDataStore.getSearchGenre().genres.map { Genre(id = it.id, name = it.name) }
    }

    suspend fun loadRuntimes(id: Long): Int = withContext(Dispatchers.IO) {
        remoteDataStore.getSearchRuntimes(id).runtime
    }

    suspend fun loadActor(movieId: Long?): List<Actor> = withContext(Dispatchers.IO) {
        return@withContext remoteDataStore.getSearchActor(movieId).actor2.map { actor2 ->
            Actor(
                id = actor2.id,
                name = actor2.name,
                picture = BASE_URL_MOVIES + actor2.profile_path
            )
        }
    }

    suspend fun loadMovies(seachMovie: String): List<Movie> = withContext(Dispatchers.IO) {
        val genres: List<Genre> = loadGenre()
        val genresMap = genres.associateBy { it.id }
        return@withContext remoteDataStore.getMovie(seachMovie).movieForSeach.map { movie2 ->
            Movie(
                id = movie2.id,
                title = movie2.title,
                posterPicture = BASE_URL_MOVIES + movie2.posterPicture,
                backdropPicture = BASE_URL_MOVIES + movie2.backdropPicture,
                runtime = loadRuntimes(movie2.id),
                ratings = movie2.vote_average,
                overview = movie2.overview,
                vote_count = movie2.vote_count,
                genreIds = movie2.genreIds.map {
                    genresMap[it] ?: throw IllegalArgumentException("Genre not found")
                },
                actors = listOf(),
                adult = if (movie2.adult) 16 else 13
            )
        }
    }
}
