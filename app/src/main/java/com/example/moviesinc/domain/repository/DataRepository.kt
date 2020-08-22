package com.example.moviesinc.domain.repository

import com.example.moviesinc.domain.local.ILocalHelper
import com.example.moviesinc.domain.local.db.RatedMoviesEntity
import com.example.moviesinc.domain.remote.IApiHelper
import com.example.moviesinc.model.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val localHelper: ILocalHelper, private val apiHelper: IApiHelper)
    : IDataRepository {

    override fun saveGuestSession(sessionId: String) {
        localHelper.saveGuestSession(sessionId)
    }

    override fun getGuestSession(): String {
        return localHelper.getGuestSession()
    }

    override fun saveImageConfig(imageConfig: ImageConfigurations) {
        localHelper.saveImageConfig(imageConfig)
    }

    override fun getImageConfig(): ImageConfigurations {
        return localHelper.getImageConfig()
    }


    override fun insertRatedMoviesList(list: List<RatedMoviesEntity>) {
        localHelper.insertRatedMoviesList(list)
    }

    override fun insertRatedMovie(ratedMovie: RatedMoviesEntity) {
        localHelper.insertRatedMovie(ratedMovie)
    }

    override fun getSavedRatedMovies(): Observable<List<RatedMoviesEntity>> {
        return localHelper.getRatedMovies()
    }

    override fun isMovieExisted(movieId: Int): Observable<Boolean> {
        return localHelper.isMovieExisted(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun searchForMovie(movieId: Int): Observable<RatedMoviesEntity> {
        return localHelper.searchForMovie(movieId)
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

    override fun getGuestRatedMovies(sessionId: String, apiKey: String): Observable<RatedMoviesModel> {
        return apiHelper.getRatedMovies(sessionId, apiKey)
    }
}