package com.example.moviesinc.domain.repository

import com.example.moviesinc.model.*
import io.reactivex.Observable

interface IDataRepository {

    fun saveImageConfig(imageConfig: ImageConfigurations)

    fun getImageConfig(): ImageConfigurations

    fun getConfiguration(apiKey: String): Observable<ConfigurationModel>

    fun getNowPlayingMovies(apiKey: String, page: Int): Observable<MoviesModel>

    fun getMovieDetails(movieId: Int, apiKey: String): Observable<MovieDetailsModel>

    fun getMovieCredits(movieId: Int, apiKey: String): Observable<MovieCreditsModel>
}