package ru.firstSet.kotlinOne.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//@Database(entities = [MovieEntity::class,ActorEntity::class,GenreEntity::class], version = 1)
@Database(entities = [MovieEntity::class, GenreEntity::class,ActorEntity::class], version = 12, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDAO: MovieDAO
//    abstract val actorDAO: ActorDAO

    companion object {
        var instance: MovieDatabase? =null

        fun createMovieDatabaseInstance(applicationContext: Context): MovieDatabase {
            if (this.instance == null) {
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
