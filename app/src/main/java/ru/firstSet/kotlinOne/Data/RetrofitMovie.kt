package ru.firstSet.kotlinOne.Data

import android.content.Context
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
import ru.firstSet.kotlinOne.Genre
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RetrofitMovie {
    var coroutineScope = CoroutineScope(Dispatchers.Main)



    suspend fun loadMovies(): List<Movie> = withContext(Dispatchers.IO) {
//    suspend fun loadMovies(): List<Movie2> = withContext(Dispatchers.IO) {


//            data: String,
        val genres: List<Genre> = loadGenre()
//            actors: List<Actor>) {
        val genresMap = genres.associateBy { it.id }
//            val actorsMap = actors.associateBy { it.id }
        return@withContext moviesApi.getSearchMovie().movie2.map { movie2 ->
//            Movie(
            Movie(
                id = movie2.id,
                title = movie2.title,
                posterPicture =   BASE_URL_MOVIES+movie2.posterPicture,
                backdropPicture =  BASE_URL_MOVIES+movie2.backdropPicture,
                       runtime = 40,
                ratings = movie2.vote_average,
                overview = movie2.overview,
                vote_count = movie2.vote_count,

                    genreIds = movie2.genreIds!!.map {
                        genresMap[it] ?: throw IllegalArgumentException("Genre not found")
                    },
//                    actors = movie2.actors.map {
//                        actorsMap[it] ?: throw IllegalArgumentException("Actor not found")
//                    },
                    actors = mutableListOf(Actor(1003, "https://image.tmdb.org/t/p/w342/q7dYamebioHRuvb9EWeSw8yTEfS.jpg","Jean Reno")),
                    adult = if (movie2.adult) 16 else 13
            )

        }

    }

suspend fun loadGenre(): List<Genre> = withContext(Dispatchers.IO) {
        moviesApi.getSearchGenre().genres.map { Genre(id = it.id, name = it.name) }
}
    @Serializable
    data class Movie2(
        @SerialName("id")
        val id: Int,
        @SerialName("backdrop_path")
        val backdropPicture: String?,
        @SerialName("title")
        val title: String,
        @SerialName("poster_path")
        val posterPicture: String?,
//        убираю бакдроп, основная картинка на месте.

//        val runtime: Int,
        @SerialName("genre_ids")
        val genreIds: List<Int>?,
//        val actors: List<Int>,
        @SerialName("vote_average")
        val vote_average: Float,
        val overview: String,
        val adult: Boolean,
        val vote_count: Int
    )


//        "genre_ids": [
//        14,
//        28,
//        80
//        ],
//        "vote_average": 5.91


    @Serializable
    private data class ResultAll(
//        @SerialName("dates")
//        val dates:Date,
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


    private interface MoviesApi {
//        @GET("search/movie?api_key=${apiKey}&language=ru-ru&query=2&include_adult=false")
        @GET("movie/now_playing?api_key=${apiKey}&language=ru-ru&query=2&include_adult=false")
        suspend fun getSearchMovie(): ResultAll

        @GET("genre/movie/list?api_key=${apiKey}&language=ru-ru")
        suspend fun getSearchGenre(): ResultGenre
//
//        suspend fun getSearchActor(): ResultAll
//
    }

    class MoviesApiHeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val originalHttpUrl = originalRequest.url
            val request = originalHttpUrl.newBuilder()
                .addQueryParameter(API_KEY_HEADER, apiKey)
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
        private const val API_KEY_HEADER = "x-api-key"
        const val apiKey = "f1eaa713b8b88ceef63a9cd8be1f7920"
        val BASE_URL = "https://api.themoviedb.org/3/"
        val BASE_URL_MOVIES = "https://image.tmdb.org/t/p/original"
    }
}
