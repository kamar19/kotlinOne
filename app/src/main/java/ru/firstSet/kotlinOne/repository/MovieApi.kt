package ru.firstSet.kotlinOne.data

import retrofit2.http.GET
import retrofit2.http.Path
import ru.firstSet.kotlinOne.ResultGenre

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
