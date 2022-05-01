package com.ahmed.data.di

import android.content.Context
import androidx.room.Room
import com.ahmed.data.local.CountriesDAO
import com.ahmed.data.local.CurrencyDAO
import com.ahmed.data.local.CurrencyDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Provides
    fun provideCurrencyDao(appDatabase: CurrencyDataBase): CurrencyDAO {
        return appDatabase.getCurrencyDao()
    }
    @Provides
    fun provideCountryDao(appDatabase: CurrencyDataBase): CountriesDAO {
        return appDatabase.getCountryDao()
    }

    @Provides
    @Singleton
    fun provideCurrencyDatabase(@ApplicationContext appContext: Context)
            : CurrencyDataBase {
        return Room.databaseBuilder(
            appContext,
            CurrencyDataBase::class.java,
            "Currency_Database"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
}