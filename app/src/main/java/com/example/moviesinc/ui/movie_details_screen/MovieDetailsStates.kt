package com.example.moviesinc.ui.movie_details_screen

import com.example.moviesinc.model.MovieCreditsModel
import com.example.moviesinc.model.MovieDetailsModel

sealed class MovieDetailsStates

sealed class FetchDetailsState: MovieDetailsStates() {

    object Loading: FetchDetailsState()

    data class SuccessDetails(val data: Pair<MovieDetailsModel, MovieCreditsModel>): FetchDetailsState()

    data class ErrorDetails(val error: String): FetchDetailsState()
}

sealed class RatingState: MovieDetailsStates() {

    object LoadRating: RatingState()

    object SuccessRating: RatingState()

    data class ErrorRating(val error: String): RatingState()
}

sealed class CheckState: MovieDetailsStates() {

    data class ExistedRating(val ratingValue: Double): CheckState()

    object NoRatingDetected: CheckState()
}
