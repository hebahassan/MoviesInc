package com.example.moviesinc.domain.local

import com.example.moviesinc.domain.local.db.IDatabaseHelper
import com.example.moviesinc.domain.local.db.RatedMoviesEntity
import com.example.moviesinc.domain.local.pref.IPrefHelper
import com.example.moviesinc.model.ImageConfigurations
import io.reactivex.Observable
import javax.inject.Inject

class LocalHelper @Inject constructor(private val prefHelper: IPrefHelper, private val dbHelper: IDatabaseHelper)
    : ILocalHelper {

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

    override fun insertRatedMoviesList(list: List<RatedMoviesEntity>) {
        dbHelper.insertRatedMoviesList(list)
    }

    override fun insertRatedMovie(ratedMovie: RatedMoviesEntity) {
        dbHelper.insertRatedMovie(ratedMovie)
    }

    override fun getRatedMovies(): Observable<List<RatedMoviesEntity>> {
        return dbHelper.getRatedMovies()
    }

    override fun isMovieExisted(movieId: Int): Observable<Boolean> {
        return dbHelper.isMovieExisted(movieId)
    }

    override fun searchForMovie(movieId: Int): Observable<RatedMoviesEntity> {
        return dbHelper.searchForMovie(movieId)
    }
}