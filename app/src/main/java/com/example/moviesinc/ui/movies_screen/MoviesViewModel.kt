package com.example.moviesinc.ui.movies_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesinc.domain.repository.IDataRepository
import com.example.moviesinc.model.ImageConfigurations
import com.example.moviesinc.ui.base.BaseViewModel
import com.example.moviesinc.utils.Constant
import javax.inject.Inject

class MoviesViewModel @Inject constructor(private val dataRepository: IDataRepository)
    : BaseViewModel() {

    private val _moviesState = MutableLiveData<MoviesStates>()
    val moviesState: LiveData<MoviesStates> get() = _moviesState

    init {
        getNowPlayingMovies()
    }

    private fun saveImageConfig(imageConfig: ImageConfigurations) = dataRepository.saveImageConfig(imageConfig)

    fun getImageConfig() = dataRepository.getImageConfig()

    fun getNowPlayingMovies() {
        disposable.add(
            dataRepository.getConfiguration(Constant.API.API_KEY)
                .doOnNext {
                    val imageConfig = ImageConfigurations(it.images.secureBaseUrl, it.images.posterSizes, it.images.profileSizes)
                    saveImageConfig(imageConfig)
                }
                .flatMap { dataRepository.getNowPlayingMovies(Constant.API.API_KEY, 1) }
                .doOnSubscribe { _moviesState.value = MoviesStates.LoadingState }
                .map { it.results.sortedBy { movie -> movie.title } }
                .subscribe({
                    _moviesState.value = MoviesStates.SuccessState(it)
                }, {
                    _moviesState.value = MoviesStates.ErrorState(it.message ?: "Error in retrieving data")
                })
        )
    }
}