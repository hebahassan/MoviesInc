package com.example.moviesinc.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.moviesinc.domain.local.ILocalHelper
import com.example.moviesinc.domain.local.LocalHelper
import com.example.moviesinc.domain.local.db.AppDatabase
import com.example.moviesinc.domain.local.db.DatabaseHelper
import com.example.moviesinc.domain.local.db.IDatabaseHelper
import com.example.moviesinc.domain.local.pref.IPrefHelper
import com.example.moviesinc.domain.local.pref.PrefHelper
import com.example.moviesinc.domain.remote.IApiHelper
import com.example.moviesinc.domain.repository.DataRepository
import com.example.moviesinc.domain.repository.IDataRepository
import com.example.moviesinc.utils.Constant
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class DomainModule {

    companion object {

        @Singleton
        @Provides
        fun provideApiHelper(retrofit: Retrofit): IApiHelper {
            return retrofit.create(IApiHelper::class.java)
        }

        @Singleton
        @Provides
        fun provideSharedPref(application: Application): SharedPreferences {
            return application.getSharedPreferences(Constant.Pref.PREF_NAME, Context.MODE_PRIVATE)
        }

        @Singleton
        @Provides
        fun provideSharedPrefEditor(pref: SharedPreferences): SharedPreferences.Editor {
            return pref.edit()
        }

        @Singleton
        @Provides
        fun provideIPrefHelper(pref: SharedPreferences, editor: SharedPreferences.Editor): IPrefHelper {
            return PrefHelper(
                pref,
                editor
            )
        }

        @Singleton
        @Provides
        fun provideIDatabaseHelper(appDatabase: AppDatabase): IDatabaseHelper {
            return DatabaseHelper(appDatabase)
        }

        @Singleton
        @Provides
        fun provideILocalHelper(prefHelper: IPrefHelper, dbHelper: IDatabaseHelper): ILocalHelper {
            return LocalHelper(prefHelper, dbHelper)
        }

        @Singleton
        @Provides
        fun provideIDataRepository(localHelper: ILocalHelper, apiHelper: IApiHelper): IDataRepository {
            return DataRepository(localHelper, apiHelper)
        }
    }
}