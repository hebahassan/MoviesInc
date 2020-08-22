package com.example.moviesinc.domain.repository

import com.example.moviesinc.domain.local.pref.IPrefHelper
import com.example.moviesinc.domain.remote.IApiHelper
import com.example.moviesinc.model.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val prefHelper: IPrefHelper, private val apiHelper: IApiHelper)
    : IDataRepository {

    override fun saveGuestSession(sessionId: String) {
        prefHelper.saveGuestSession(sessionId)
    }

    override fun getGuestSession(): String {
        return prefHelper.getGuestSession()
    }

    override fun saveImageConfig(imageConfig: ImageConfigurations) {
        prefHelper.saveImageConfig(imageConfig)
    }

    override fun getImageConfig(): ImageConfigurations {
        return prefHelper.getImageConfig()
    }

    override fun getConfiguration(apiKey: String): Observable<ConfigurationModel> {
        return apiHelper.getConfiguration(apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun createGuestSession(apiKey: String): Observable<GuestSessionModule> {
        return apiHelper.createGuestSession(apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getNowPlayingMovies(apiKey: String, page: Int): Observable<MoviesModel> {
        return apiHelper.getNowPlayingMovies(apiKey, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getMovieDetails(movieId: Int, apiKey: String): Observable<MovieDetailsModel> {
        return apiHelper.getMovieDetails(movieId, apiKey)
    }

    override fun getMovieCredits(movieId: Int, apiKey: String): Observable<MovieCreditsModel> {
        return apiHelper.getMovieCredits(movieId, apiKey)
    }

    override fun postMovieRating(
        movieId: Int,
        apiKey: String,
        guestSessionId: String,
        ratingBody: RatingBody
    ): Completable {
        return apiHelper.postMovieRating(movieId, apiKey, guestSessionId, ratingBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getRatedMovies(sessionId: String, apiKey: String): Observable<RatedMoviesModel> {
        return apiHelper.getRatedMovies(sessionId, apiKey)
    }
}