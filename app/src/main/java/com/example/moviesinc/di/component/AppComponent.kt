package com.example.moviesinc.di.component

import android.app.Application
import com.example.moviesinc.app.MoviesApplication
import com.example.moviesinc.di.module.ActivityModule
import com.example.moviesinc.di.module.AppModule
import com.example.moviesinc.di.module.DomainModule
import com.example.moviesinc.di.module.ViewModelFactoryModule
import com.example.moviesinc.domain.local.PrefHelper
import com.example.moviesinc.domain.repository.DataRepository
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    DomainModule::class,
    ActivityModule::class,
    ViewModelFactoryModule::class])

interface AppComponent: AndroidInjector<MoviesApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}