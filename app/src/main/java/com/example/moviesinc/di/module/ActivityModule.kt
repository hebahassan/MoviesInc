package com.example.moviesinc.di.module

import com.example.moviesinc.di.annotation.MoviesScope
import com.example.moviesinc.ui.movies_screen.MoviesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @MoviesScope
    @ContributesAndroidInjector(modules = [MoviesViewModelModule::class, MoviesActivityModule::class])
    abstract fun contributeMoviesActivity(): MoviesActivity
}