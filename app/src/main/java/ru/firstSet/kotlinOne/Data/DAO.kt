package ru.firstSet.kotlinOne.Data

import androidx.room.*

@Dao
interface MovieDAO {

//    @Transaction
//    @Query("SELECT  moviesTable.id, moviesTable.title FROM moviesTable")
//    suspend fun getMoviesAndGenre(): List<Movie>

    //    @Query("SELECT moviesTable.id, moviesTable.title, moviesTable.posterPicture," +
//            "  moviesTable.backdropPicture, moviesTable.runtime, moviesTable.ratings, moviesTable.overview, " +
//            "moviesTable.adult, moviesTable.vote_count, moviesTable. " +
//            "FROM moviesTable, genreTable )
//    "SELECT employee.name, employee.salary, department.name AS department_name " +
//    "FROM employee, department " +
//    "WHERE department.id == employee.department_id")
//    @Query("SELECT id, title, backdropPicture,posterPicture,runtime,ratings, " +
//            "overview, adult,vote_count FROM moviesTable")
    @Transaction
    @Query("SELECT * FROM moviesTable WHERE seachMovie= :seachMovie ORDER BY ratings DESC")
    suspend fun getAllMovies(seachMovie: String): List<MovieEntity>


//    @Transaction
//    @Query("SELECT * FROM moviesTable, genreTable WHERE  moviesTable.id==genreTable.genreMovieId")
//    suspend fun getMovieAndGenre(): List<Movie>

    @Query("SELECT * FROM genreTable WHERE genreMovieId = :idMovie")
    suspend fun getGenresFromSQL(idMovie:Long): List<GenreEntity>

    @Query("SELECT * FROM genreTable")
    suspend fun getAllGenres(): List<GenreEntity>

    @Query("SELECT * FROM actorTable WHERE actorMovieId ==:idMovie")
    suspend fun getActorFromSQL(idMovie:Long): List<ActorEntity>




//    @Query("SELECT genreId, genreName, genreMovieId FROM genreTable ")
//    suspend fun getGenres(): List<Genre>


//
//    @Transaction
//    @Query("SELECT * FROM moviesTable")
////    @Query("SELECT * FROM DialogPojo WHERE id = :dialogId")
//    suspend fun getMoviesAndActorAndGenre(): List<MovieAndActorAndGenre>
//
//    @Transaction
//    @Delete
//    suspend fun deleteAllMovies()

    //    @Insert
//    suspend fun insertAllMovie(list: List<Movie>)
//
//    @Insert
//    suspend fun insertAllMovieAndActorAndGenre(list: List<MovieAndActorAndGenre>)
//    @Transaction
//    public suspend fun insertMovieAndActorAndGenre(movies: List<Movie>) {
////        insertMovieAndActorAndGenre(movies)
//        movies.forEach() {
//            insertGenres(it.genres)
//        }
//    }


    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveGenres(genres: List<GenreEntity>)//:List<GenreEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveActors(actors: List<ActorEntity>)//:List<GenreEntity>


    @Transaction
    @Query("DELETE FROM moviesTable")
    suspend fun deleteAllMovies()

    @Transaction
    @Query("DELETE FROM actorTable")
    suspend fun deleteActors()

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insert(actorEntity: ActorEntity)
}


//
//@Dao
//interface ActorDAO {
//    @Query("SELECT * FROM actorTable")
//    suspend fun getActorsByIdMovie(movieId: Int): List<Actor>
//
//    @Delete
//    suspend fun deleteAllActors()
//
//    @Insert
//    suspend fun insertAllActor(list: List<Actor>)
//}
//
//@Dao
//interface GenreDAO {
//    @Query("SELECT * FROM genreTable")
//    suspend fun getGenresByIdMovie(movieId: Int): List<Genre>
//
//    @Delete
//    suspend fun deleteAllGenres()
//
//    @Insert
//    suspend fun insertAllGenre(list: List<Genre>)
//}