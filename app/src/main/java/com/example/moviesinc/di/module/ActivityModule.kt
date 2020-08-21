package com.example.moviesinc.di.module

import com.example.moviesinc.di.annotation.MovieDetailsScope
import com.example.moviesinc.di.annotation.MoviesScope
import com.example.moviesinc.di.ui_module.MovieDetailsViewModelModule
import com.example.moviesinc.di.ui_module.MoviesActivityModule
import com.example.moviesinc.di.ui_module.MoviesViewModelModule
import com.example.moviesinc.ui.movie_details_screen.MovieDetailsActivity
import com.example.moviesinc.ui.movies_screen.MoviesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @MoviesScope
    @ContributesAndroidInjector(modules = [MoviesViewModelModule::class, MoviesActivityModule::class])
    abstract fun contributeMoviesActivity(): MoviesActivity

    @MovieDetailsScope
    @ContributesAndroidInjector(modules = [MovieDetailsViewModelModule::class])
    abstract fun contributeMovieDetailsActivity(): MovieDetailsActivity
}