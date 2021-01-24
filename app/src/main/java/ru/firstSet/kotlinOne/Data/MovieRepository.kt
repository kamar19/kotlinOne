package ru.firstSet.kotlinOne.Data

import android.content.Context
import android.util.Log
import kotlinx.coroutines.*
import ru.firstSet.kotlinOne.Data.MovieDatabase.Companion.createMovieDatabaseInstance
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MovieRepository(context: Context) {
    private val BASE_URL_MOVIES = "https://image.tmdb.org/t/p/original"
    private val remoteDataStore = ru.firstSet.kotlinOne.Data.RemoteDataStore
    val movieDataBase: MovieDatabase = createMovieDatabaseInstance(context)

    suspend fun loadGenre(idMovie: Long): List<GenreEntity> {
        var genres: List<GenreEntity> = listOf()

//        withContext(Dispatchers.IO) {
//            movieDataBase.movieDAO.getGenresFromSQL(idMovie)
//        }
//        if (genres.size == 0) {         // можно и не качать новую порчию инфы
        genres = remoteDataStore.getGenreFromNet().genres.map {
            GenreEntity(
                idGenre = it.id,
                name = it.name,
                genreMovieId = idMovie
            )
//            }
        }
        return genres
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

    suspend fun loadMoviesFromNET(seachMovie: String): List<Movie> = withContext(Dispatchers.IO) {
        return@withContext remoteDataStore.getMovie(seachMovie).movieForNETS.map { movie2 ->
            val genres: List<GenreEntity> = loadGenre(movie2.id)
            val genresMap = genres.associateBy { it.idGenre }
            Movie(
                id = movie2.id,
                title = movie2.title,
                posterPicture = BASE_URL_MOVIES + movie2.posterPicture,
                backdropPicture = BASE_URL_MOVIES + movie2.backdropPicture,
                runtime = loadRuntimes(movie2.id),
                ratings = movie2.vote_average,
                overview = movie2.overview,
                vote_count = movie2.vote_count,
                genres = movie2.genreIds.map {
                    genresMap[it] ?: throw IllegalArgumentException("Genre not found")
                },
                actors = listOf(),
                adult = if (movie2.adult) 16 else 13
            )
        }
    }


    suspend fun readMovieFromDb(): List<Movie> {
        var movies: List<Movie> = listOf()
        withContext(Dispatchers.IO) {
            val moviesEntity: List<MovieEntity> = movieDataBase.movieDAO.getAllMovies()
            movies = movieEntityToMovie(moviesEntity)
            Log.v("readMovieFromDb-movies", "${movies.size}")

        }
        return movies
    }

    private fun movieToMovieEntity(movieList: List<Movie>): List<MovieEntity> {
        val newMovie: MutableList<MovieEntity> = mutableListOf()
        for (movies in movieList) {
            val movie: MovieEntity = MovieEntity(
                id = movies.id,
                title = movies.title,
                posterPicture = movies.posterPicture,
                backdropPicture = movies.backdropPicture,
                runtime = movies.runtime,
                ratings = movies.ratings,
                overview = movies.overview,
                adult = movies.adult,
                vote_count = movies.vote_count,
            )
            newMovie.add(movie)
        }
        return newMovie
    }


    private suspend fun movieEntityToMovie(movieEntity: List<MovieEntity>): List<Movie> {
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
//                genres =  listOf(),
                genres = movieDataBase.movieDAO.getGenresFromSQL(movies.id.toInt()),
                actors = listOf()
            )
            newMovie.add(movie)
        }
        return newMovie
    }

    suspend fun saveMovieToDB(movies: List<Movie>) {
        return withContext(Dispatchers.IO) {
            Log.v("saveMovieToDB (movies)", " ${movies.size}")
//            movieDataBase.movieDAO.insertAll(movies)
//            movieDataBase.movieDAO.DeleteAllMovies()

            movieDataBase.movieDAO.insertMovies(movieToMovieEntity(movies))

            movies.forEach() {
                movieDataBase.movieDAO.insertGenres(it.genres)
                Log.v("insertGenres", " ${it.genres.size}")
            }
        }
    }

}
