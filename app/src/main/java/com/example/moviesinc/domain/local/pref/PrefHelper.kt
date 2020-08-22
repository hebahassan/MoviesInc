package com.example.moviesinc.domain.local.pref

import android.content.SharedPreferences
import com.example.moviesinc.model.ImageConfigurations
import com.example.moviesinc.utils.Constant
import com.google.gson.Gson
import javax.inject.Inject

class PrefHelper @Inject constructor(
    private val pref: SharedPreferences, private val editor: SharedPreferences.Editor)
    : IPrefHelper {

    override fun saveGuestSession(sessionId: String) {
        editor.putString(Constant.Pref.SESSION_ID, sessionId)
        editor.apply()
    }

    override fun getGuestSession(): String {
        return pref.getString(Constant.Pref.SESSION_ID, "") ?: ""
    }

    override fun saveImageConfig(imageConfig: ImageConfigurations) {
        val gson = Gson()
        val json = gson.toJson(imageConfig)
        editor.putString(Constant.Pref.CONFIG, json)
        editor.apply()
    }

    override fun getImageConfig(): ImageConfigurations {
        val gson = Gson()
        val json = pref.getString(Constant.Pref.CONFIG, "") ?: ""
        return gson.fromJson(json, ImageConfigurations::class.java)
    }
}