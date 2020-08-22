package com.example.moviesinc.utils

object Constant {

    object API {
        const val URL = "https://api.themoviedb.org/3/"
        const val CONTENT_TYPE = "application/json;charset=utf-8"
        const val API_KEY = "707a0023b0aef8ed2e3f2c5ce9df315f"

        const val GUEST_SESSION = "authentication/guest_session/new"
        const val NOW_PLAYING = "movie/now_playing"
        const val CONFIGURATION = "configuration"
        const val MOVIE = "movie"
        const val CREDITS = "credits"
        const val RATE_MOVIE = "rating"
        const val RATED_MOVIES = "guest_session/{guest_session_id}/rated/movies"
    }

    object Params {
        const val API_KEY = "api_key"
        const val PAGE = "page"
        const val MOVIE_ID = "movie_id"
        const val GUEST_SESSION_ID = "guest_session_id"
        const val CONTENT_TYPE = "Content-Type"
    }

    object Pref {
        const val PREF_NAME = "movies_pref"
        const val SESSION_ID = "session_id"
        const val CONFIG = "image_config"
    }

    object Extras {
        const val MOVIE_ID = "movie_id"
    }
}