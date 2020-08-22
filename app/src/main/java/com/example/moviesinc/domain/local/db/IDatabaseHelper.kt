package com.example.moviesinc.domain.local.db

import io.reactivex.Observable

interface IDatabaseHelper {

    fun insertRatedMoviesList(list: List<RatedMoviesEntity>)

    fun insertRatedMovie(ratedMovie: RatedMoviesEntity)

    fun getRatedMovies(): Observable<List<RatedMoviesEntity>>

    fun isMovieExisted(movieId: Int): Observable<Boolean>

    fun searchForMovie(movieId: Int): Observable<RatedMoviesEntity>
}