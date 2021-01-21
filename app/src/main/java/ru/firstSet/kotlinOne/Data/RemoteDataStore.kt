package ru.firstSet.kotlinOne.Data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.firstSet.kotlinOne.ResultGenre

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val apiKey = "f1eaa713b8b88ceef63a9cd8be1f7920"

class RemoteDataStore {

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

    private val moviesApi: MoviesApi = this.retrofit.create(MoviesApi::class.java)

    class MoviesApiHeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val originalHttpUrl = originalRequest.url
            val request = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", apiKey)
                .build()
            val url = originalRequest.newBuilder().url(request).build()
            return chain.proceed(url)
        }
    }

    suspend fun getSearchGenre(): ResultGenre {
        return moviesApi.getSearchGenre()
    }

    suspend fun getSearchActor(movie_id: Long?): ResultActor {
        return moviesApi.getSearchActor(movie_id)
    }

    suspend fun getMovie(seachMovie: String): ResultMovie {
        return moviesApi.getMovie(seachMovie)
    }

    suspend fun getSearchRuntimes(movie_id: Long?): ResultDetails {
        return moviesApi.getSearchRuntimes(movie_id)
    }
}