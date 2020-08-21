package com.example.moviesinc.domain.remote

import com.example.moviesinc.model.ConfigurationModel
import com.example.moviesinc.model.MoviesModel
import com.example.moviesinc.utils.Constant
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiHelper {

    @GET(Constant.API.GUEST_SESSION)
    fun createGuestSession(@Query(Constant.Params.API_KEY) apiKey: String)

    @GET(Constant.API.CONFIGURATION)
    fun getConfiguration(@Query(Constant.Params.API_KEY) apiKey: String): Observable<ConfigurationModel>

    @GET(Constant.API.NOW_PLAYING)
    fun getNowPlayingMovies(@Query(Constant.Params.API_KEY) apiKey: String, @Query(Constant.Params.PAGE) page: Int)
            : Observable<MoviesModel>
}