package ru.firstSet.kotlinOne.DataSource
import ru.firstSet.kotlinOne.Genre

object GenresDataSource  {
    private var genresList :List<Genre> = listOf()
    fun getGenresList(): List<Genre> {
        return this.genresList
    }
    fun setGenresList(genreList :List<Genre>){
        this.genresList=genreList
    }
}