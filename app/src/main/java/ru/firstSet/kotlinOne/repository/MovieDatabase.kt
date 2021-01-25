package ru.firstSet.kotlinOne.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.firstSet.kotlinOne.GenreEntity
import ru.firstSet.kotlinOne.data.ActorEntity
import ru.firstSet.kotlinOne.data.DBContract
import ru.firstSet.kotlinOne.data.MovieDAO
import ru.firstSet.kotlinOne.data.MovieEntity

@Database(
    entities = [MovieEntity::class, GenreEntity::class, ActorEntity::class],
    version = 12,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDAO: MovieDAO

    companion object {
        var instance: MovieDatabase? = null
        fun createMovieDatabaseInstance(applicationContext: Context): MovieDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    applicationContext,
                    MovieDatabase::class.java,
                    DBContract.DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance as MovieDatabase
        }
    }
}
