package ru.firstSet.kotlinOne.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.firstSet.kotlinOne.Genre

@Dao
interface MovieDAO {
    @Query("SELECT  moviesTable.id, moviesTable.title FROM moviesTable")
    suspend fun getMoviesAndGenre(): List<Movie>
    @Query("SELECT moviesTable.id, moviesTable.title FROM moviesTable")
    suspend fun getAllMovies(): List<Movie>
    @Delete
    suspend fun deleteAllMovies()
    @Insert
    suspend fun insertAllMovie(list: List<Movie>)
}

@Dao
interface ActorDAO {
    @Query("SELECT * FROM actorTable")
    suspend fun getActorsByIdMovie(movieId: Int): List<Actor>
    @Delete
    suspend fun deleteAllActors()
    @Insert
    suspend fun insertAllActor(list: List<Actor>)
}
@Dao
interface GenreDAO {
    @Query("SELECT * FROM genreTable")
    suspend fun getGenresByIdMovie(movieId: Int): List<Genre>
    @Delete
    suspend fun deleteAllGenres()
    @Insert
    suspend fun insertAllGenre(list: List<Genre>)
}