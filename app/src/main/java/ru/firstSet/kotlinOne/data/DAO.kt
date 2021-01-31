package ru.firstSet.kotlinOne.data

import androidx.room.*
import ru.firstSet.kotlinOne.Genre

@Dao
interface MovieDAO {
    @Transaction
    @Query("SELECT * FROM moviesTable WHERE seachMovie= :seachMovie ORDER BY ratings DESC")
    suspend fun getMoviesSeach(seachMovie: String): List<MovieRelation>

    @Transaction
    @Query("SELECT * FROM moviesTable  WHERE id= :idMovie")
    suspend fun getMovie(idMovie: Long): MovieRelation

    @Query("SELECT * FROM actorTable WHERE actorId ==:actorId")
    suspend fun getActor(actorId: Int): Actor

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveActors(actors: List<Actor>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGenres(genres: List<Genre>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
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