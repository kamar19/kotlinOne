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

    suspend fun loadGenreFromNET(idMovie: Long): List<GenreEntity> =
        remoteDataStore.getGenreFromNet().genres.map {
            GenreEntity(
                idGenre = it.id,
                name = it.name,
                genreMovieId = idMovie
            )
        }


    suspend fun loadRuntimesFromNET(id: Long): Int = withContext(Dispatchers.IO) {
        remoteDataStore.getSearchRuntimes(id).runtime
    }

    suspend fun loadActorFromNET(movieId: Long): List<ActorEntity> = withContext(Dispatchers.IO) {
        return@withContext remoteDataStore.getSearchActor(movieId).actor2.map {
            ActorEntity(
                actorId = it.id,
                actorName = it.name,
                picture = BASE_URL_MOVIES + it.profile_path,
                actorMovieId = movieId
            )
        }
    }

    suspend fun readActorFromDb(idMovie: Long): List<ActorEntity> {
        var actorList: List<ActorEntity> = listOf()
        withContext(Dispatchers.IO) {
            actorList = movieDataBase.movieDAO.getActorFromSQL(idMovie)
            Log.v("readActorFromDb ", " ${actorList.size}")
            actorList.forEach() {
                Log.v("readActorFromDb ", " ${it.actorMovieId},  ${it.actorName}, ${it.actorId}")
            }
        }
        return actorList
    }

    suspend fun loadMoviesFromNET(seachMovie: String): List<Movie> = withContext(Dispatchers.IO) {
        return@withContext remoteDataStore.getMovie(seachMovie).movieForNETS.map { movie2 ->
            val genres: List<GenreEntity> = loadGenreFromNET(movie2.id)
            val genresMap = genres.associateBy { it.idGenre }
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
                actors = listOf(),
                adult = if (movie2.adult) 16 else 13,
            )
        }
    }


    suspend fun readMovieFromDb(seachMovie: SeachMovie): List<Movie> =
        withContext(Dispatchers.IO) {
            Log.v("readMovieFromDb  ", " ${seachMovie.seachMovie}")

            val moviesEntity: List<MovieEntity> = movieDataBase.movieDAO.getAllMovies(seachMovie.seachMovie)
            convertMovieEntityToMovie(moviesEntity)
        }

    suspend fun readGenreFromDb(idMovie: Long): List<GenreEntity> {
        var listGenre: List<GenreEntity> = mutableListOf()
        withContext(Dispatchers.IO) {
            Log.v("idMovie =  ", " ${idMovie}")
            listGenre = movieDataBase.movieDAO.getGenresFromSQL(idMovie)

        }
//        listGenre.forEach(){
            Log.v("listGenreFromDb ", " ${listGenre.size}")
//        }
        readAllGenres()
        return listGenre
    }

    suspend fun readAllGenres(): List<GenreEntity> {
        var listGenre: List<GenreEntity> = listOf()
        withContext(Dispatchers.IO) {
            listGenre = movieDataBase.movieDAO.getAllGenres()
            Log.v("readAllGenres", " ${listGenre.size}")
        }

        listGenre.forEach() {
            Log.v("list ", " ${it.genreMovieId},  ${it.name}, ${it.idGenre}")
        }
        return listGenre
    }


    private fun convertMovieToMovieEntity(movieList: List<Movie>,seachMovie:SeachMovie): List<MovieEntity> {
        val newMovie: MutableList<MovieEntity> = mutableListOf()
        movieList.forEach() {
            val movie: MovieEntity = MovieEntity(
                id = it.id,
                title = it.title,
                posterPicture = it.posterPicture,
                backdropPicture = it.backdropPicture,
                runtime = it.runtime,
                ratings = it.ratings,
                overview = it.overview,
                adult = it.adult,
                vote_count = it.vote_count,
                seachMovie=seachMovie.seachMovie

            )
            newMovie.add(movie)
        }
        return newMovie
    }


    private suspend fun convertMovieEntityToMovie(movieEntity: List<MovieEntity>): List<Movie> {
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
                genres = readGenreFromDb(movies.id),
                actors = listOf()
            )
            newMovie.add(movie)
            Log.v("convertMovieEntity", " ${movie.genres.size}")
            Log.v("convertMovieEntity", " ${movie.title}")

        }
        return newMovie
    }

    suspend fun saveMovieToDB(movies: List<Movie>,seachMovie:SeachMovie) {
        return withContext(Dispatchers.IO) {
            Log.v("saveMovieToDB (movies)", " ${movies.size}")
//            if (movies.size > 0) {
//                movieDataBase.movieDAO.deleteAllMovies()
//            }
            movieDataBase.movieDAO.saveMovies(convertMovieToMovieEntity(movies,seachMovie))
            movies.forEach() {
                movieDataBase.movieDAO.saveGenres(it.genres)
                Log.v("insertGenres", " ${it.genres.size}")
            }
        }
    }

    suspend fun saveActorToDB(actors: List<ActorEntity>) {
        return withContext(Dispatchers.IO) {
            Log.v("saveActorToDB", " ${actors.size}")
            if (actors.size > 0) {
                movieDataBase.movieDAO.deleteActors()
            }
            movieDataBase.movieDAO.saveActors(actors)
        }
    }


}


