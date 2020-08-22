package com.example.moviesinc.domain.local.db

import io.reactivex.Observable
import javax.inject.Inject

class DatabaseHelper @Inject constructor(private val appDatabase: AppDatabase): IDatabaseHelper {

    override fun insertRatedMoviesList(list: List<RatedMoviesEntity>) {
        appDatabase.ratedMoviesDao().insertRatedMoviesList(list)
    }

    override fun insertRatedMovie(ratedMovie: RatedMoviesEntity) {
        appDatabase.ratedMoviesDao().insertRatedMovie(ratedMovie)
    }

    override fun getRatedMovies(): Observable<List<RatedMoviesEntity>> {
        return appDatabase.ratedMoviesDao().getRatedMovies()
    }

    override fun isMovieExisted(movieId: Int): Observable<Boolean> {
        return appDatabase.ratedMoviesDao().isMovieExisted(movieId)
    }

    override fun searchForMovie(movieId: Int): Observable<RatedMoviesEntity> {
        return appDatabase.ratedMoviesDao().searchForMovie(movieId)
    }
}