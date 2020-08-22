package com.example.moviesinc.di.module

import com.example.moviesinc.di.annotation.MovieDetailsScope
import com.example.moviesinc.di.annotation.MoviesScope
import com.example.moviesinc.di.annotation.SplashScope
import com.example.moviesinc.di.ui_module.*
import com.example.moviesinc.ui.movie_details_screen.MovieDetailsActivity
import com.example.moviesinc.ui.movies_screen.MoviesActivity
import com.example.moviesinc.ui.splash_screen.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @SplashScope
    @ContributesAndroidInjector(modules = [SplashViewModelModule::class])
    abstract fun contributeSplashActivity(): SplashActivity

    @MoviesScope
    @ContributesAndroidInjector(modules = [MoviesViewModelModule::class, MoviesActivityModule::class])
    abstract fun contributeMoviesActivity(): MoviesActivity

    @MovieDetailsScope
    @ContributesAndroidInjector(modules = [MovieDetailsViewModelModule::class, MovieDetailsActivityModule::class])
    abstract fun contributeMovieDetailsActivity(): MovieDetailsActivity
}