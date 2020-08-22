package com.example.moviesinc.domain.local

import com.example.moviesinc.domain.local.db.RatedMoviesEntity
import com.example.moviesinc.model.ImageConfigurations
import io.reactivex.Observable

interface ILocalHelper {

    fun saveGuestSession(sessionId: String)

    fun getGuestSession(): String

    fun saveImageConfig(imageConfig: ImageConfigurations)

    fun getImageConfig(): ImageConfigurations


    fun insertRatedMoviesList(list: List<RatedMoviesEntity>)

    fun insertRatedMovie(ratedMovie: RatedMoviesEntity)

    fun getRatedMovies(): Observable<List<RatedMoviesEntity>>

    fun isMovieExisted(movieId: Int): Observable<Boolean>

    fun searchForMovie(movieId: Int): Observable<RatedMoviesEntity>
}