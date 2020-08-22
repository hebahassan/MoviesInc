package com.example.moviesinc.ui.splash_screen

sealed class SplashStates {

    object ExistedList: SplashStates()

    object Success: SplashStates()

    data class Error(val error: String): SplashStates()
}