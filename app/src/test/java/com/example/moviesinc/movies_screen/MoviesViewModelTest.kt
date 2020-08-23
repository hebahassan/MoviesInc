package com.example.moviesinc.movies_screen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.moviesinc.MockRepository
import com.example.moviesinc.domain.repository.IDataRepository
import com.example.moviesinc.ui.movies_screen.MoviesStates
import com.example.moviesinc.ui.movies_screen.MoviesViewModel
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private var viewModel: MoviesViewModel? = null

    @Mock
    lateinit var dataRepository: IDataRepository

    @Mock
    lateinit var observer: Observer<MoviesStates>

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        viewModel = MoviesViewModel(dataRepository)
        viewModel?.moviesState?.observeForever(observer)
    }

    @Test
    fun testGetNowPlayingMovies() {
        Mockito.`when`(dataRepository.getConfiguration(MockRepository.API_KEY))
            .thenReturn((Observable.just(MockRepository.configModel)))

        Mockito.`when`(dataRepository.getNowPlayingMovies(MockRepository.API_KEY, 1))
            .thenReturn((Observable.just(MockRepository.moviesModel)))

        viewModel?.getNowPlayingMovies()

        verify(dataRepository).getConfiguration(MockRepository.API_KEY)
        verify(dataRepository).saveImageConfig(MockRepository.imageConfig)
        verify(dataRepository).getNowPlayingMovies(MockRepository.API_KEY, 1)
        verifyNoMoreInteractions(dataRepository)

        verify(observer).onChanged(MoviesStates.LoadingState)
        verify(observer).onChanged(MoviesStates.SuccessState(MockRepository.sortedMoviesList))
        verifyNoMoreInteractions(observer)
    }

    @After
    fun tearDown() {
        viewModel?.moviesState?.removeObserver(observer)
        viewModel = null
    }
}