package com.example.moviesinc.domain.local

import com.example.moviesinc.model.ImageConfigurations

interface IPrefHelper {

    fun saveGuestSession(sessionId: String)

    fun getGuestSession(): String

    fun saveImageConfig(imageConfig: ImageConfigurations)

    fun getImageConfig(): ImageConfigurations
}