package com.example.moviesinc.ui.movie_details_screen

import com.example.moviesinc.model.MovieCreditsModel
import com.example.moviesinc.model.MovieDetailsModel

sealed class MovieDetailsStates {

    object LoadingDetails: MovieDetailsStates()

    data class SuccessDetails(val data: Pair<MovieDetailsModel, MovieCreditsModel>): MovieDetailsStates()

    data class ErrorDetails(val error: String): MovieDetailsStates()
}