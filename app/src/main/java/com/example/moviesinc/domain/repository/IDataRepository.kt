package com.example.moviesinc.domain.repository

import com.example.moviesinc.model.ConfigurationModel
import com.example.moviesinc.model.ImageConfig
import com.example.moviesinc.model.MoviesModel
import io.reactivex.Observable

interface IDataRepository {

    fun saveImageConfig(imageConfig: ImageConfig)

    fun getImageConfig(): ImageConfig

    fun getConfiguration(apiKey: String): Observable<ConfigurationModel>

    fun getNowPlayingMovies(apiKey: String, page: Int): Observable<MoviesModel>
}