package com.example.moviesinc.splash_screen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.moviesinc.MockRepository
import com.example.moviesinc.domain.repository.IDataRepository
import com.example.moviesinc.model.RatedMoviesResult
import com.example.moviesinc.ui.splash_screen.SplashStates
import com.example.moviesinc.ui.splash_screen.SplashViewModel
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SplashViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @InjectMocks
    lateinit var viewModel: SplashViewModel

    @Mock
    lateinit var dataRepository: IDataRepository

    @Mock
    lateinit var observer: Observer<SplashStates>

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        viewModel.splashState.observeForever(observer)
    }

    @After
    fun tearDown() {
        viewModel.splashState.removeObserver(observer)
    }

}