package com.example.moviesinc.ui.splash_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesinc.domain.repository.IDataRepository
import com.example.moviesinc.ui.base.BaseViewModel
import com.example.moviesinc.utils.Constant
import javax.inject.Inject

//TODO: get list of rated movies and save it to realm DB then compare movieId with the saved list
class SplashViewModel @Inject constructor(private val dataRepository: IDataRepository)
    : BaseViewModel() {

    private val _splashState = MutableLiveData<SplashStates>()
    val splashState: LiveData<SplashStates> get() = _splashState

    init {
        when(getGuestSession()) {
            "" -> createGuestSession()
            else -> _splashState.value = SplashStates.ExistedId
        }
    }

    private fun getGuestSession() = dataRepository.getGuestSession()

    private fun saveGuestSession(sessionId: String) = dataRepository.saveGuestSession(sessionId)

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