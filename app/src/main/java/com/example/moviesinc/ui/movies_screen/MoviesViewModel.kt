package com.example.moviesinc.ui.movies_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesinc.domain.repository.DataRepository
import com.example.moviesinc.model.ImageConfig
import com.example.moviesinc.model.MovieResult
import com.example.moviesinc.utils.Constant
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    private val disposable: CompositeDisposable, private val dataRepository: DataRepository)
    : ViewModel() {

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
                .subscribe({
                    setImagesUrls(it.results)
                }, {
                    _moviesState.value = MoviesStates.ErrorState(it.message ?: "Error in retrieving data")
                })
        )
    }

    private fun setImagesUrls(movies: List<MovieResult>) {
        val config = getImageConfig()
        val url =  config.secureBaseUrl
        val posterSize = config.posterSizes.first()

        movies.forEach {
            val newPath = "$url$posterSize${it.posterPath}"
            it.posterPath = newPath
        }

        sortMoviesAlphabetically(movies)
    }

    private fun sortMoviesAlphabetically(movies: List<MovieResult>){
        val sortedList = movies.sortedBy { it.title }
        _moviesState.value = MoviesStates.SuccessState(sortedList)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}