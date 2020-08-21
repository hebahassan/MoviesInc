package com.example.moviesinc.di.module

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.moviesinc.R
import com.example.moviesinc.domain.remote.IApiHelper
import com.example.moviesinc.utils.Constant
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    companion object {

        @Singleton
        @Provides
        fun provideRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(Constant.API.URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Singleton
        @Provides
        fun provideRequestOptions(): RequestOptions {
            return RequestOptions()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
        }

        @Singleton
        @Provides
        fun provideGlideInstance(application: Application, requestOptions: RequestOptions): RequestManager {
            return Glide.with(application)
                .setDefaultRequestOptions(requestOptions)
        }
    }
}