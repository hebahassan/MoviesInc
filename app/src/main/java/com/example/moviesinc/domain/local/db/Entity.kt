package com.example.moviesinc.domain.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RatedMovies")
data class RatedMoviesEntity (@PrimaryKey val movieId: Int,
                   val rating: Double)