package ru.firstSet.kotlinOne

import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.junit.Assert
import org.junit.Test
import org.junit.Assert.*
import ru.firstSet.kotlinOne.data.*
import ru.firstSet.kotlinOne.repository.MovieDatabase
import ru.firstSet.kotlinOne.repository.RepositoryDB

class RepositoryDBTest {

    @Test
    fun repositoryDB_convertMovieToMovieEntity() {
        val movieDatabase: MovieDatabase = mock<MovieDatabase>()
        val repositoryDB: RepositoryDB = RepositoryDB(movieDatabase)

        val movieList = movieListTest
        val seachMovie: SeachMovie = SeachMovie.MovieUpComing
        val movieEntity: List<MovieEntity>
//        только public методы

//        movieEntity = RepositoryDB.convertMovieToMovieEntity(movieList,seachMovie)
//
//        Assert.assertArrayEquals(movieEntity,)

    }



}