package com.example.moviesinc

import com.example.moviesinc.model.*
import com.example.moviesinc.utils.Constant

object MockRepository {

    const val API_KEY = Constant.API.API_KEY

    val images = Images(backdropSizes = listOf("92", "150"), baseUrl = "baseUrl", logoSizes = listOf("92", "150"),
        posterSizes = listOf("92", "150"), profileSizes = listOf("92", "150"), secureBaseUrl = "secureUrl",
        stillSizes = listOf("92", "150"))

    val configModel = ConfigurationModel(changeKeys = listOf("1", "2", "3"), images = images)

    val imageConfig = ImageConfigurations(secureBaseUrl = "secureUrl",
        posterSizes = listOf("92", "150"), profileSizes = listOf("92", "150"))

    val movieResult1 = MovieResult(adult = false, backdropPath = "backUpPath", genreIds = listOf(1, 2), id = 234,
        originalLanguage = "English", originalTitle = "The Secret Garden", overview = "", popularity = 5.6, posterPath = "posterPath",
        releaseDate = "2020-03-20", title = "The Secret Garden", video = true, voteAverage = 7.3, voteCount = 100)

    val movieResult2 = MovieResult(adult = true, backdropPath = "backUpPath", genreIds = listOf(5, 6), id = 235,
        originalLanguage = "English", originalTitle = "1919", overview = "", popularity = 8.2, posterPath = "posterPath",
        releaseDate = "2019-07-07", title = "1919", video = false, voteAverage = 8.5, voteCount = 200)

    val moviesModel = MoviesModel(listOf(movieResult1, movieResult2))

    val sortedMoviesList = listOf(movieResult2, movieResult1)


    val ratedMoviesResult = RatedMoviesResult(adult = false, backdropPath = "backUpPath", genreIds = listOf(1, 2), id = 234,
        originalLanguage = "English", originalTitle = "The Secret Garden", overview = "", popularity = 5.6, posterPath = "posterPath",
        releaseDate = "2020-03-20", title = "The Secret Garden", video = true, voteAverage = 7.3, voteCount = 100, rating = 5.0)
}