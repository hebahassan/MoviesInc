package com.example.moviesinc.di.ui_module

import androidx.lifecycle.ViewModel
import com.example.moviesinc.di.annotation.ViewModelKey
import com.example.moviesinc.ui.splash_screen.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SplashViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel
}