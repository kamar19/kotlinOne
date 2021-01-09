package ru.firstSet.kotlinOne.Data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import ru.firstSet.kotlinOne.Genre
import ru.firstSet.kotlinOne.ResultGenre
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
object MovieRepository {
    private const val BASE_URL_MOVIES = "https://image.tmdb.org/t/p/original"

    var coroutineScope = CoroutineScope(Dispatchers.Main)
    suspend fun loadGenre(): List<Genre> = withContext(Dispatchers.IO) {
        moviesApi.getSearchGenre().genres.map { Genre(id = it.id, name = it.name) }
    }

    suspend fun loadRuntimes(id: Long): Int = withContext(Dispatchers.IO) {
        moviesApi.getSearchRuntimes(id).runtime
    }

    suspend fun loadActor(movieId: Long?): List<Actor> = withContext(Dispatchers.IO) {
        return@withContext moviesApi.getSearchActor(movieId).actor2.map { actor2 ->
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
        return@withContext moviesApi.getMovie(seachMovie).movieForSeach.map { movie2 ->
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
