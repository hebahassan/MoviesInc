package com.example.moviesinc.model


import com.google.gson.annotations.SerializedName

data class GuestSessionModule(
    @SerializedName("expires_at")
    val expiresAt: String,
    @SerializedName("guest_session_id")
    val guestSessionId: String,
    @SerializedName("success")
    val success: Boolean
)