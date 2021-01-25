package ru.firstSet.kotlinOne.data

import androidx.room.*
import ru.firstSet.kotlinOne.GenreEntity

@Dao
interface MovieDAO {
    @Query("SELECT * FROM moviesTable WHERE seachMovie= :seachMovie ORDER BY ratings DESC")
    suspend fun getAllMovies(seachMovie: String): List<MovieEntity>

    @Query("SELECT * FROM genreTable WHERE genreMovieId = :idMovie")
    suspend fun getGenresFromSQL(idMovie: Long): List<GenreEntity>

    @Query("SELECT * FROM actorTable WHERE actorMovieId ==:idMovie")
    suspend fun getActorFromSQL(idMovie: Long): List<ActorEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveGenres(genres: List<GenreEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveActors(actors: List<ActorEntity>)

    @Query("DELETE FROM moviesTable")
    suspend fun deleteAllMovies()

    @Query("DELETE FROM actorTable")
    suspend fun deleteActors()
}