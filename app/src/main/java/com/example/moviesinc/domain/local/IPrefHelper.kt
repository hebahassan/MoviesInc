package com.example.moviesinc.domain.local

import com.example.moviesinc.model.ImageConfig

interface IPrefHelper {

    fun saveGuestSession(sessionId: String)

    fun getGuestSession(): String

    fun saveImageConfig(imageConfig: ImageConfig)

    fun getImageConfig(): ImageConfig
}