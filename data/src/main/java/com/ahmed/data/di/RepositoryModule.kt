package com.ahmed.data.di

import com.ahmed.data.repository.CountriesRepositoryImpl
import com.ahmed.data.repository.CurrencyRepositoryImpl
import com.ahmed.domain.repository.CountriesRepository
import com.ahmed.domain.repository.CurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideCurrencyRepository
                (currencyRepositoryImpl: CurrencyRepositoryImpl): CurrencyRepository
    @Binds
    @Singleton
    abstract fun provideCountriesRepository
                (countriesRepositoryImpl: CountriesRepositoryImpl): CountriesRepository




}