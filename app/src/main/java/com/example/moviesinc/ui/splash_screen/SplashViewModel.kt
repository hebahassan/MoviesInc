package com.example.moviesinc.ui.splash_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesinc.domain.local.db.RatedMoviesEntity
import com.example.moviesinc.domain.repository.IDataRepository
import com.example.moviesinc.model.RatedMoviesResult
import com.example.moviesinc.ui.base.BaseViewModel
import com.example.moviesinc.utils.Constant
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val dataRepository: IDataRepository) :
    BaseViewModel() {

    private val _splashState = MutableLiveData<SplashStates>()
    val splashState: LiveData<SplashStates> get() = _splashState

    init {
        when (getGuestSession()) {
            "" -> createGuestSession()
            else -> getRatedMoviesList()
        }
    }

    private fun saveGuestSession(sessionId: String) = dataRepository.saveGuestSession(sessionId)

    private fun getGuestSession() = dataRepository.getGuestSession()

    private fun convertRatedMoviesToEntity(list: List<RatedMoviesResult>): List<RatedMoviesEntity> {
        val ratedMoviesEntityList = ArrayList<RatedMoviesEntity>()
        list.forEach {
            val entity = RatedMoviesEntity(it.id, it.rating)
            ratedMoviesEntityList.add(entity)
        }
        return ratedMoviesEntityList
    }

    private fun insertRatedMoviesList(list: List<RatedMoviesEntity>) =
        dataRepository.insertRatedMoviesList(list)

    private fun getRatedMoviesList() {
        val connectableObservable =
            dataRepository.getSavedRatedMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .replay()

        disposable.add(
            connectableObservable
                .filter { it.isNotEmpty() }
                .subscribe { _splashState.value = SplashStates.ExistedList }
        )

        connectableObservable.connect()

        disposable.add(
            connectableObservable
                .filter { it.isEmpty() }
                .observeOn(Schedulers.io())
                .flatMap { dataRepository.getGuestRatedMovies(getGuestSession(), Constant.API.API_KEY) }
                .map { convertRatedMoviesToEntity(it.results) }
                .doOnNext { insertRatedMoviesList(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _splashState.value = SplashStates.Success
                }, {
                    _splashState.value = SplashStates.Error(it.message ?: "Error retrieving rated movies")
                })
        )
    }

    private fun createGuestSession() {
        disposable.add(
            dataRepository.createGuestSession(Constant.API.API_KEY)
                .doOnNext { saveGuestSession(it.guestSessionId) }
                .subscribe({
                    _splashState.value = SplashStates.Success
                }, {
                    _splashState.value = SplashStates.Error("Error retrieving session id")
                })
        )
    }
}