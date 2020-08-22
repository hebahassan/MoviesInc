package com.example.moviesinc.domain.repository

import com.example.moviesinc.domain.local.db.RatedMoviesEntity
import com.example.moviesinc.model.*
import io.reactivex.Completable
import io.reactivex.Observable

interface IDataRepository {

    fun saveGuestSession(sessionId: String)

    fun getGuestSession(): String

    fun saveImageConfig(imageConfig: ImageConfigurations)

    fun getImageConfig(): ImageConfigurations


    fun insertRatedMoviesList(list: List<RatedMoviesEntity>)

    fun insertRatedMovie(ratedMovie: RatedMoviesEntity)

    fun getSavedRatedMovies(): Observable<List<RatedMoviesEntity>>

    fun isMovieExisted(movieId: Int): Observable<Boolean>

    fun searchForMovie(movieId: Int): Observable<RatedMoviesEntity>


    fun createGuestSession(apiKey: String): Observable<GuestSessionModule>

    fun getConfiguration(apiKey: String): Observable<ConfigurationModel>

    fun getNowPlayingMovies(apiKey: String, page: Int): Observable<MoviesModel>

    fun getMovieDetails(movieId: Int, apiKey: String, appendToResponse: String): Observable<MovieDetailsModel>

    fun postMovieRating(movieId: Int, apiKey: String, guestSessionId: String, ratingBody: RatingBody): Completable

    fun getGuestRatedMovies(sessionId: String, apiKey: String): Observable<RatedMoviesModel>
}