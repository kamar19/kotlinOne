package ru.firstSet.kotlinOne.data

import androidx.room.*
import ru.firstSet.kotlinOne.GenreEntity
import ru.firstSet.kotlinOne.Genre

@Dao
interface MovieDAO {
    @Transaction
    @Query("SELECT * FROM moviesTable WHERE seachMovie= :seachMovie ORDER BY ratings DESC")
    suspend fun getMoviesSeach(seachMovie: String): List<MovieRelation>

    @Transaction
    @Query("SELECT * FROM moviesTable  WHERE id= :idMovie")
    suspend fun getMovie(idMovie: Long): MovieRelation

    @Query("SELECT * FROM genreTable WHERE genreMovieId = :idMovie")
    suspend fun getGenresFromSQL(idMovie: Long): List<GenreEntity>

    @Query("SELECT * FROM genreTable")
    suspend fun getAllGenre(): List<GenreEntity>

    @Transaction
    @Query("SELECT * FROM moviesTable")
    suspend fun getAllMovie(): List<MovieRelation>



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveMovies(movies: List<MovieEntity>)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveActors(actors: List<ActorEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGenres(genres: List<GenreEntity>): List< Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateGenres(genre: GenreEntity)

    @Transaction
    fun upsertGenres(genres: List<GenreEntity>) {
        val rowIDs = insertGenres(genres)

        val genresToUpdate = rowIDs.mapIndexedNotNull { index, rowID ->
            if (rowID == -1L) null else genres[index] }
        genresToUpdate.forEach { updateGenres(it) }
    }







}