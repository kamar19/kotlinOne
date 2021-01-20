package ru.firstSet.kotlinOne.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDAO: MovieDAO
    abstract val actorDAO: ActorDAO

    companion object {
        fun create(applicationContext: Context): MovieDatabase = Room.databaseBuilder(
            applicationContext,
            MovieDatabase::class.java,
            DBContract.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}