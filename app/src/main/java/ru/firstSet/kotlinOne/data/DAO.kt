package ru.firstSet.kotlinOne.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.firstSet.kotlinOne.Genre

@Dao
interface MovieDAO {
    @Query("SELECT * FROM moviesTable WHERE seachMovie= :seachMovie ORDER BY ratings DESC")
    fun getMoviesSeach(seachMovie: String): Flow<List<MovieRelation>>

    @Query("SELECT * FROM moviesTable WHERE seachMovie= :seachMovie ORDER BY ratings DESC")
    fun getAllMovies(seachMovie: String): List<MovieRelation>

    @Transaction
    @Query("SELECT * FROM moviesTable  WHERE id= :idMovie")
    suspend fun getMovie(idMovie: Long): MovieRelation

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveMovie(movie: MovieEntity)

    @Transaction
    suspend fun saveMovieAndRelation(movies: List<Movie>,seachMovie: SeachMovie) {
        movies.forEach {
            saveMovie(MovieEntity(
                it.id,
                it.title,
                it.posterPicture,
                it.backdropPicture,
                it.runtime,
                it.ratings,
                it.overview,
                it.adult,
                it.vote_count,
                seachMovie.seachMovie
            ))
            upsertGenres(it.genres)
            saveActors(it.actors)
        }
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