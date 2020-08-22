package com.example.moviesinc.domain.repository

import com.example.moviesinc.model.*
import io.reactivex.Completable
import io.reactivex.Observable

interface IDataRepository {

    fun saveGuestSession(sessionId: String)

    fun getGuestSession(): String

    fun saveImageConfig(imageConfig: ImageConfigurations)

    fun getImageConfig(): ImageConfigurations


    fun createGuestSession(apiKey: String): Observable<GuestSessionModule>

    fun getConfiguration(apiKey: String): Observable<ConfigurationModel>

    fun getNowPlayingMovies(apiKey: String, page: Int): Observable<MoviesModel>

    fun getMovieDetails(movieId: Int, apiKey: String): Observable<MovieDetailsModel>

    fun getMovieCredits(movieId: Int, apiKey: String): Observable<MovieCreditsModel>

    fun postMovieRating(movieId: Int, apiKey: String, guestSessionId: String, ratingBody: RatingBody): Completable

    fun getRatedMovies(sessionId: String, apiKey: String): Observable<RatedMoviesModel>
}