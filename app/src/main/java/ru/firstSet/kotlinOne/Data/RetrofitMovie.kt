package ru.firstSet.kotlinOne.Data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
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
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RetrofitMovie {
    var coroutineScope = CoroutineScope(Dispatchers.Main)
    suspend fun loadGenre(): List<Genre> = withContext(Dispatchers.IO) {
        moviesApi.getSearchGenre().genres.map { Genre(id = it.id, name = it.name) }
    }


    suspend fun loadRuntimes(id: Long):Int = withContext(Dispatchers.IO){
//        return@withContext moviesApi.getSearchRuntimes(id).runtime
        moviesApi.getSearchRuntimes(id).runtime
//        { actor2 ->
//
//        }
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

    suspend fun loadMovies(): List<Movie> = withContext(Dispatchers.IO) {
        val genres: List<Genre> = loadGenre()
        val genresMap = genres.associateBy { it.id }
        return@withContext moviesApi.getSearchMovie().movie2.map { movie2 ->
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



    @Serializable
    data class Movie2(
        @SerialName("id")
        val id: Long,
        @SerialName("backdrop_path")
        val backdropPicture: String?,
        @SerialName("title")
        val title: String,
        @SerialName("poster_path")
        val posterPicture: String?,
        @SerialName("genre_ids")
        val genreIds: List<Int>,
        @SerialName("vote_average")
        val vote_average: Float,
        val overview: String,
        val adult: Boolean,
        val vote_count: Int
//        val runtime: Int
//        var actors: List<Actor>,
    )


    @Serializable
    private data class ResultAll(
        @SerialName("page")
        val page: Int,
        @SerialName("results")
        val movie2: List<Movie2>
    )

    @Serializable
    private data class ResultGenre(
        @SerialName("genres")
        val genres: List<Genre>
    )

    @Serializable
    private data class ResultActor(
        @SerialName("id")
        val page: Int,
        @SerialName("cast")
        val actor2: List<Actor2>
    )

    @Serializable
    data class Actor2(
        @SerialName("id")
        val id: Int,
        @SerialName("name")
        val name: String,
        @SerialName("profile_path")
        val profile_path: String?
    )
    @Serializable
    data class ResultDetails(
        @SerialName("id")
        val id: Int,
        @SerialName("runtime")
        val runtime: Int
    )


    private interface MoviesApi {
        @GET("movie/now_playing?language=ru-ru&query=2&include_adult=false")
        suspend fun getSearchMovie(): ResultAll
        @GET("genre/movie/list?&language=ru-ru")
        suspend fun getSearchGenre(): ResultGenre
        @GET("movie/{movie_id}/credits?&language=ru-ru")
        suspend fun getSearchActor(@Path("movie_id")movie_id: Long?): ResultActor
        @GET("movie/{movie_id}&language=ru-ru")
        suspend fun  getSearchRuntimes(@Path("movie_id")movie_id: Long?): ResultDetails
    }

    class MoviesApiHeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val originalHttpUrl = originalRequest.url
            val request = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key",apiKey)
                .build()
            val url = originalRequest.newBuilder().url(request).build()
            return chain.proceed(url)
        }
    }

    private val client = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(MoviesApiHeaderInterceptor())
        .build()

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @Suppress("EXPERIMENTAL_API_USAGE")
    private val retrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    private val moviesApi: MoviesApi = retrofit.create(MoviesApi::class.java)

    companion object {
        private val TAG = "RetrofitMovie"
        const val apiKey = "f1eaa713b8b88ceef63a9cd8be1f7920"
        val BASE_URL = "https://api.themoviedb.org/3/"
        val BASE_URL_MOVIES = "https://image.tmdb.org/t/p/original"
    }
}
