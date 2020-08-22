package com.example.moviesinc.ui.movie_details_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesinc.domain.local.db.RatedMoviesEntity
import com.example.moviesinc.domain.repository.IDataRepository
import com.example.moviesinc.model.ImageConfigurations
import com.example.moviesinc.model.MovieCreditsModel
import com.example.moviesinc.model.MovieDetailsModel
import com.example.moviesinc.model.RatingBody
import com.example.moviesinc.ui.base.BaseViewModel
import com.example.moviesinc.utils.Constant
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(private val dataRepository: IDataRepository): BaseViewModel() {

    private val _fetchDetailsState = MutableLiveData<FetchDetailsState>()
    val fetchDetailsState: LiveData<FetchDetailsState> get() = _fetchDetailsState

    private val _ratingState = MutableLiveData<RatingState>()
    val ratingState: LiveData<RatingState> get() = _ratingState

    private val _checkState = MutableLiveData<CheckState>()
    val checkState: LiveData<CheckState> get() = _checkState

    private val imageConfigurations: ImageConfigurations by lazy { getImageConfig() }

    private fun getImageConfig() = dataRepository.getImageConfig()

    private fun getGuestSession() = dataRepository.getGuestSession()

    private fun insertRatedMovie(ratedMovie: RatedMoviesEntity) = dataRepository.insertRatedMovie(ratedMovie)

    fun getPosterPath(): String {
        return "${imageConfigurations.secureBaseUrl}${imageConfigurations.posterSizes.first()}"
    }

    fun getProfilePath(): String {
        return "${imageConfigurations.secureBaseUrl}${imageConfigurations.profileSizes.first()}"
    }

    fun searchForMovie(movieId: Int) {
        val connectable = dataRepository.isMovieExisted(movieId).replay()

        disposable.add(
            connectable.filter { it }
                .observeOn(Schedulers.io())
                .flatMap { dataRepository.searchForMovie(movieId) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _checkState.value = CheckState.ExistedRating(it.rating)
                }, {
                    _checkState.value = CheckState.NoRatingDetected
                })
        )

        connectable.connect()

        disposable.add(
            connectable.filter { !it }
                .subscribe({
                    _checkState.value = CheckState.NoRatingDetected
                }, {
                    _checkState.value = CheckState.NoRatingDetected
                })
        )
    }

    //TODO: combine 2 apis with append_to_request
    fun getMovieDetails(movieId: Int) {
        disposable.add(
            Observable.zip(dataRepository.getMovieDetails(movieId, Constant.API.API_KEY),
                dataRepository.getMovieCredits(movieId, Constant.API.API_KEY),
                BiFunction<MovieDetailsModel, MovieCreditsModel, Pair<MovieDetailsModel, MovieCreditsModel>> { t1, t2 ->
                    Pair(t1, t2)
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _fetchDetailsState.value = FetchDetailsState.Loading
                }
                .subscribe({
                    _fetchDetailsState.value = FetchDetailsState.SuccessDetails(it)
                }, {
                    _fetchDetailsState.value = FetchDetailsState.ErrorDetails("Error retrieving data")
                })
        )
    }

    fun rateMovie(movieId: Int, ratingValue: Double) {
        disposable.add(
            dataRepository.postMovieRating(movieId, Constant.API.API_KEY, getGuestSession(), RatingBody(ratingValue))
                .doOnSubscribe { _ratingState.value = RatingState.LoadRating }
                .observeOn(Schedulers.io())
                .doFinally {
                    val ratedMovie = RatedMoviesEntity(movieId, ratingValue)
                    insertRatedMovie(ratedMovie)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _ratingState.value = RatingState.SuccessRating
                }, {
                    _ratingState.value = RatingState.ErrorRating("Couldn't rate the movie, please try again")
                })
        )
    }
}