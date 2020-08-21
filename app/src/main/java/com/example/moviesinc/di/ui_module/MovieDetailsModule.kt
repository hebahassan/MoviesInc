package com.example.moviesinc.di.ui_module

import androidx.lifecycle.ViewModel
import com.bumptech.glide.RequestManager
import com.example.moviesinc.di.annotation.MovieDetailsScope
import com.example.moviesinc.di.annotation.ViewModelKey
import com.example.moviesinc.ui.movie_details_screen.CastAdapter
import com.example.moviesinc.ui.movie_details_screen.MovieDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class MovieDetailsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    abstract fun bindMovieDetailsViewModel(viewModel: MovieDetailsViewModel): ViewModel
}

@Module
class MovieDetailsActivityModule {

    companion object {

        @MovieDetailsScope
        @Provides
        fun provideCastAdapter(requestManager: RequestManager) = CastAdapter(requestManager)
    }
}