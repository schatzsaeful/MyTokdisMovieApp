package com.example.moviecatalogue.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviecatalogue.data.source.local.entity.*

@Database(entities = [MoviePopularEntity::class, MovieTopRatedEntity::class, MovieUpcomingEntity::class, MovieNowPlayingEntity::class], version = 3)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao?

    companion object {
        private var instance: MovieDatabase? = null
        @JvmStatic
        @Synchronized
        fun getFavoriteDatabase(context: Context): MovieDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                    MovieDatabase::class.java, "favorite.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return instance
        }
    }
}