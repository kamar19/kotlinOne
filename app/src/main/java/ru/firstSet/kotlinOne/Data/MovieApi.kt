package ru.firstSet.kotlinOne.Data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import ru.firstSet.kotlinOne.ResultGenre

private const val TAG = "RetrofitMovie"
private const val apiKey = "f1eaa713b8b88ceef63a9cd8be1f7920"
private const val BASE_URL = "https://api.themoviedb.org/3/"

    interface MoviesApi {
        @GET("genre/movie/list?&language=ru-ru")
        suspend fun getSearchGenre(): ResultGenre

        @GET("movie/{movie_id}/credits?&language=ru-ru")
        suspend fun getSearchActor(@Path("movie_id") movie_id: Long?): ResultActor

        @GET("movie/{movie_id}&language=ru-ru")
        suspend fun getSearchRuntimes(@Path("movie_id") movie_id: Long?): ResultDetails

        @GET("movie/{seachMovie}?language=ru-ru&query=2&include_adult=false")
        suspend fun getMovie(@Path("seachMovie") seachMovie: String): ResultMovie
}

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

public val moviesApi: MoviesApi = retrofit.create(MoviesApi::class.java)

