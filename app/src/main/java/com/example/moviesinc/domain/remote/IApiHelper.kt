package com.example.moviesinc.domain.remote

import com.example.moviesinc.model.*
import com.example.moviesinc.utils.Constant
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface IApiHelper {

    @GET(Constant.API.GUEST_SESSION)
    fun createGuestSession(@Query(Constant.Params.API_KEY) apiKey: String): Observable<GuestSessionModule>

    @GET(Constant.API.CONFIGURATION)
    fun getConfiguration(@Query(Constant.Params.API_KEY) apiKey: String): Observable<ConfigurationModel>

    @GET(Constant.API.NOW_PLAYING)
    fun getNowPlayingMovies(@Query(Constant.Params.API_KEY) apiKey: String,
                            @Query(Constant.Params.PAGE) page: Int)
            : Observable<MoviesModel>

    @GET("${Constant.API.MOVIE}/{${Constant.Params.MOVIE_ID}}")
    fun getMovieDetails(@Path(Constant.Params.MOVIE_ID) movieId: Int,
                        @Query(Constant.Params.API_KEY) apiKey: String)
            : Observable<MovieDetailsModel>

    @GET("${Constant.API.MOVIE}/{movie_id}/${Constant.API.CREDITS}")
    fun getMovieCredits(@Path(Constant.Params.MOVIE_ID) movieId: Int,
                        @Query(Constant.Params.API_KEY) apiKey: String)
            : Observable<MovieCreditsModel>

    @POST("${Constant.API.MOVIE}/{movie_id}/${Constant.API.RATE_MOVIE}")
    @Headers("${Constant.Params.CONTENT_TYPE}: ${Constant.API.CONTENT_TYPE}")
    fun postMovieRating(@Path(Constant.Params.MOVIE_ID) movieId: Int,
                        @Query(Constant.Params.API_KEY) apiKey: String,
                        @Query(Constant.Params.GUEST_SESSION_ID) guestSessionId: String,
                        @Body ratingBody: RatingBody)
            : Completable

    @GET(Constant.API.RATED_MOVIES)
    fun getRatedMovies(@Path(Constant.Params.GUEST_SESSION_ID) sessionId: String,
                       @Query(Constant.Params.API_KEY) apiKey: String)
            : Observable<RatedMoviesModel>
}