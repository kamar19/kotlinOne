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
import ru.firstSet.kotlinOne.Genre


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RetrofitMovie {
    //    var coroutineScope = createCoroutineScope()
    var coroutineScope = CoroutineScope(Dispatchers.Main)
//    var movieList: List<Movie2> = listOf()
//    fun createCoroutineScope() = CoroutineScope(Job() + Dispatchers.IO)


//    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
//        Log.e(TAG, "Coroutine exception, scope active:${coroutineScope.isActive}", throwable)
//        coroutineScope = createCoroutineScope()
//    }


//
//    private var catPhotoView: ImageView? = null
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? = inflater.inflate(R.layout.fragment_ws03, container, false)
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        catPhotoView = view.findViewById(R.id.catPhotoView)
//
//        view.findViewById<Button>(R.id.loadCatView).setOnClickListener {
//            loadCats()
//        }
//    }


    suspend fun loadMovies(): List<Movie2> = withContext(Dispatchers.IO) {
//        val genres: Map<Int, Genre> =
//            MoviesApi.getSearchGenre().genres.map { Genre(id = it.id, name = it.name) }
//                .associateBy { it.id }


//        return@withContext moviesApi.getSearchMovie(). movie2.map {
        return@withContext moviesApi.getSearchMovie().movie2
//            .map {
//было             Movie(
//            Movie2(
//                id = it.id,
//                title = it.title,
//                posterPicture = it.posterPicture
//            )
//                backdropPicture = it.backdropPicture,
//                runtime = it.runtime,
//                ratings = it.ratings,
//                overview = it.overview,
//                adult = it.adult,
//                vote_count = it.vote_count
//             genreIds = map {Genre("id": 12,  "name": "Adventure")}
//            actors = map {
//                "gender": 2,
//                "id": 37919,
//                "name": "Stanislas Merhar",
//                "original_name": "Stanislas Merhar",
//                "profile_path": "https://image.tmdb.org/t/p/w342/k5XQM1XXG71GLd41hFcx8yhkxRy.jpg"
//            }),
//            (
//                "gender": 2,
//                "id": 1407184,
//                "name": "Kaaris",
//                "original_name": "Kaaris",
//                "profile_path": "https://image.tmdb.org/t/p/w342/zHxqojtMgk5aBqMQyTqcoPc4TRG.jpg"
//            )
//
//        }
//        }
    }


    @Serializable
    data class Movie2(
        val id: Int,
        val title: String,
        @SerialName("poster_path")
        val posterPicture: String
//        @SerialName("backdrop_path")
//        val backdropPicture: String,
//        val runtime: Int,
//////        @SerialName("genre_ids")
//////        val genreIds: List<Int>,
////        val actors: List<Int>,
////        @SerialName("vote_average")
//        val ratings: Float,
//        val overview: String,
//        val adult: Boolean,
//        val vote_count: Int
    )

    @Serializable
    private data class ResultAll(
        @SerialName("page")
        val page: Int,
        @SerialName("results")
        val movie2: List<Movie2>
    )


    private interface MoviesApi {
        @GET("search/movie?api_key=${apiKey}&language=ru-ru&query=2&include_adult=false")
        suspend fun getSearchMovie(): ResultAll

//        @GET("genre/movie/list")
//        suspend fun getSearchGenre(): ResultGenre
//
//        suspend fun getSearchActor(): ResultAll
//
    }

    class MoviesApiHeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val originalHttpUrl = originalRequest.url
            val request = originalHttpUrl.newBuilder()
//                .url(originalHttpUrl)
                .addQueryParameter(API_KEY_HEADER, apiKey)

//                .addHeader(API_KEY_HEADER, apiKey)
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
