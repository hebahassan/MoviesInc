package com.example.moviesinc.ui.movies_screen

import com.example.moviesinc.model.MovieResult

sealed class MoviesStates {
    object LoadingState: MoviesStates()
    data class SuccessState(val data: List<MovieResult>): MoviesStates()
    data class ErrorState(val error: String): MoviesStates()
}