package com.example.moviesinc.di.ui_module

import androidx.lifecycle.ViewModel
import com.example.moviesinc.di.annotation.ViewModelKey
import com.example.moviesinc.ui.movie_details_screen.MovieDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MovieDetailsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    abstract fun bindMovieDetailsViewModel(viewModel: MovieDetailsViewModel): ViewModel
}