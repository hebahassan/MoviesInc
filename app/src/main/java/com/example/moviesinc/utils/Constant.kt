package com.example.moviesinc.utils

object Constant {

    object API {
        const val URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "707a0023b0aef8ed2e3f2c5ce9df315f"

        const val GUEST_SESSION = "authentication/guest_session/new"
        const val NOW_PLAYING = "movie/now_playing"
        const val CONFIGURATION = "configuration"
    }

    object Params {
        const val API_KEY = "api_key"
        const val PAGE = "page"
    }

    object Pref {
        const val PREF_NAME = "movies_pref"
        const val SESSION_ID = "session_id"
        const val CONFIG = "image_config"
    }
}