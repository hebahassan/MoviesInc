package com.example.moviesinc.ui.movie_details_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesinc.domain.repository.IDataRepository
import com.example.moviesinc.model.ImageConfigurations
import com.example.moviesinc.model.MovieCreditsModel
import com.example.moviesinc.model.MovieDetailsModel
import com.example.moviesinc.ui.base.BaseViewModel
import com.example.moviesinc.utils.Constant
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(private val dataRepository: IDataRepository): BaseViewModel() {

    private val _movieDetailsState = MutableLiveData<MovieDetailsStates>()
    val movieDetailsState: LiveData<MovieDetailsStates> get() = _movieDetailsState

    private val imageConfigurations: ImageConfigurations by lazy { getImageConfig() }

    private fun getImageConfig() = dataRepository.getImageConfig()

    fun getPosterPath(): String {
        return "${imageConfigurations.secureBaseUrl}${imageConfigurations.posterSizes.first()}"
    }

    fun getProfilePath(): String {
        return "${imageConfigurations.secureBaseUrl}${imageConfigurations.profileSizes.first()}"
    }

    fun getMovieDetails(movieId: Int) {
        disposable.add(
            Observable.zip(dataRepository.getMovieDetails(movieId, Constant.API.API_KEY),
                dataRepository.getMovieCredits(movieId, Constant.API.API_KEY),
                BiFunction<MovieDetailsModel, MovieCreditsModel, Pair<MovieDetailsModel, MovieCreditsModel>> { t1, t2 ->
                    Pair(t1, t2)
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _movieDetailsState.value = MovieDetailsStates.LoadingDetails }
                .subscribe({
                    _movieDetailsState.value = MovieDetailsStates.SuccessDetails(it)
                }, {
                    _movieDetailsState.value = MovieDetailsStates.ErrorDetails("Error retrieving data")
                })
        )
    }
}