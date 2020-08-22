package com.example.moviesinc.domain.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RatedMoviesEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun ratedMoviesDao(): RatedMoviesDao
}