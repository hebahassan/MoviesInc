package com.example.moviesinc.domain.repository

import com.example.moviesinc.domain.local.IPrefHelper
import com.example.moviesinc.domain.local.PrefHelper
import com.example.moviesinc.domain.remote.IApiHelper
import com.example.moviesinc.model.ConfigurationModel
import com.example.moviesinc.model.ImageConfig
import com.example.moviesinc.model.MoviesModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val prefHelper: IPrefHelper, private val apiHelper: IApiHelper)
    : IDataRepository {

    override fun saveImageConfig(imageConfig: ImageConfig) {
        prefHelper.saveImageConfig(imageConfig)
    }

    override fun getImageConfig(): ImageConfig {
        return prefHelper.getImageConfig()
    }

    override fun getConfiguration(apiKey: String): Observable<ConfigurationModel> {
        return apiHelper.getConfiguration(apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getNowPlayingMovies(apiKey: String, page: Int): Observable<MoviesModel> {
        return apiHelper.getNowPlayingMovies(apiKey, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}