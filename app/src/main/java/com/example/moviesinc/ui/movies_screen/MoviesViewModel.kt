package com.example.moviesinc.ui.movies_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesinc.domain.repository.IDataRepository
import com.example.moviesinc.model.ImageConfig
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

    private fun saveImageConfig(imageConfig: ImageConfig) = dataRepository.saveImageConfig(imageConfig)

    private fun getImageConfig() = dataRepository.getImageConfig()

    private fun getNowPlayingMovies() {
        disposable.add(
            dataRepository.getConfiguration(Constant.API.API_KEY)
                .doOnNext {
                    val imageConfig = ImageConfig(it.images.secureBaseUrl, it.images.posterSizes)
                    saveImageConfig(imageConfig)
                }
                .flatMap { dataRepository.getNowPlayingMovies(Constant.API.API_KEY, 1) }
                .doOnSubscribe { _moviesState.value = MoviesStates.LoadingState }
                .map {
                    val config = getImageConfig()
                    val url =  config.secureBaseUrl
                    val posterSize = config.posterSizes.first()

                    it.results.forEach { movie ->
                        val newPath = "$url$posterSize${movie.posterPath}"
                        movie.posterPath = newPath
                    }
                    return@map it.results
                }
                .map { it.sortedBy { movie -> movie.title } }
                .subscribe({
                    _moviesState.value = MoviesStates.SuccessState(it)
                }, {
                    _moviesState.value = MoviesStates.ErrorState(it.message ?: "Error in retrieving data")
                })
        )
    }
}