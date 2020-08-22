package com.example.moviesinc.ui.splash_screen

sealed class SplashStates {

    object ExistedId: SplashStates()

    object Success: SplashStates()

    data class Error(val error: String): SplashStates()
}