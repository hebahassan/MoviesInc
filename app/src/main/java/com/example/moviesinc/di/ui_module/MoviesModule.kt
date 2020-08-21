package com.example.moviesinc.di.ui_module

import androidx.lifecycle.ViewModel
import com.bumptech.glide.RequestManager
import com.example.moviesinc.di.annotation.MoviesScope
import com.example.moviesinc.di.annotation.ViewModelKey
import com.example.moviesinc.ui.movies_screen.MoviesAdapter
import com.example.moviesinc.ui.movies_screen.MoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class MoviesViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    abstract fun bindMoviesViewModel(viewModel: MoviesViewModel): ViewModel
}

@Module
class MoviesActivityModule {

    companion object {

        @MoviesScope
        @Provides
        fun provideMoviesAdapter(requestManager: RequestManager) = MoviesAdapter(requestManager)
    }
}