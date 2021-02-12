package ru.firstSet.kotlinOne.data

import android.util.Log
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.firstSet.kotlinOne.Genre

@Dao
interface MovieDAO {
    @Transaction
    @Query("SELECT * FROM moviesTable WHERE seachMovie= :seachMovie ORDER BY ratings DESC")
    fun getMoviesSeach(seachMovie: String): Flow<List<MovieRelation>>

    @Query("SELECT count(*) FROM moviesTable WHERE seachMovie= :seachMovie")
    fun getCountMoviesSeach(seachMovie: String): Long

    @Transaction
    @Query("SELECT * FROM moviesTable  WHERE id= :idMovie")
    suspend fun getMovie(idMovie: Long): MovieRelation

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveMovie(movie: MovieEntity)

    @Transaction
    suspend fun saveMovieAndRelation(
        movie: MovieEntity,
        genres: List<Genre>,
        actors: List<Actor>
    ) {
        saveMovie(movie)
        upsertGenres(genres)
        saveActors(actors)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveActors(actors: List<Actor>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGenres(genres: List<Genre>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun updateGenres(genre: Genre)

    @Transaction
    fun upsertGenres(genres: List<Genre>) {
        val rowId = insertGenres(genres)
        val genresToUpdate = rowId.mapIndexedNotNull { index, rowId ->
            if (rowId == -1L) null else genres[index]
        }
        genresToUpdate.forEach { updateGenres(it) }
    }
}