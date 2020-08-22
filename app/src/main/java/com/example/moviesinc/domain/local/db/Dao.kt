package com.example.moviesinc.domain.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Observable

@Dao
interface RatedMoviesDao {

    @Insert
    fun insertRatedMoviesList(list: List<RatedMoviesEntity>)

    @Insert
    fun insertRatedMovie(ratedMovie: RatedMoviesEntity)

    @Query("SELECT * FROM RatedMovies")
    fun getRatedMovies(): Observable<List<RatedMoviesEntity>>

    @Query("SELECT EXISTS (SELECT * FROM RatedMovies WHERE movieId = :movieId)")
    fun isMovieExisted(movieId: Int): Observable<Boolean>

    @Query("SELECT * FROM RatedMovies WHERE movieId = :movieId")
    fun searchForMovie(movieId: Int): Observable<RatedMoviesEntity>
}