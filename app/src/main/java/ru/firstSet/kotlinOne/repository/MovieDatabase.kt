package ru.firstSet.kotlinOne.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.firstSet.kotlinOne.Genre
import ru.firstSet.kotlinOne.data.*

@Database(
    entities = [MovieEntity::class, Genre::class, Actor::class],
    version = 39,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    public abstract val movieDAO: MovieDAO

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
