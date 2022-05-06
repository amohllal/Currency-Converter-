package com.ahmed.data.repository

import android.util.Log
import com.ahmed.data.core.compact
import com.ahmed.data.local.CountriesDAO
import com.ahmed.data.local.CurrencyDAO
import com.ahmed.data.mapper.*
import com.ahmed.data.remote.CurrencyAPI
import com.ahmed.data.source.local.LocalDataSource
import com.ahmed.data.source.remote.RemoteDataSource
import com.ahmed.domain.entities.CountriesEntities
import com.ahmed.domain.entities.CurrenciesDate
import com.ahmed.domain.entities.CurrencyConverterEntity
import com.ahmed.domain.entities.CurrencyEntities
import com.ahmed.domain.repository.CurrencyRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.internal.operators.completable.CompletableFromAction
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : CurrencyRepository {

    override fun getAllCurrency(): Single<List<CurrencyEntities>> {
       return remoteDataSource.getCurrency().flatMap {
           CompletableFromAction{
               localDataSource.saveCurrencies(it.mapToList().mapToEntity())
           }.andThen(Single.just(it.mapToList().mapToDomain()))
       }.onErrorResumeNext{
           localDataSource.getCurrenciesFromDataBase().map {
               it.mapToRemoteResponse().mapToDomain()
           }
       }
    }



    override fun getAllCountries(): Single<List<CountriesEntities>> {
        return remoteDataSource.getCountries()
            .flatMap {
                Completable.fromAction {
                    localDataSource.saveCountries(it.mapToList().mapToCountryEntity())
                }.andThen(Single.just(it.mapToList().mapToCountriesDomain()))

            }.onErrorResumeNext {
                localDataSource.getCountriesFromDataBase().map {
                    it.mapToCountryRemoteResponse().mapToCountriesDomain()
                }
            }
    }



    override fun getConverterCurrency(
        baseCurrency: String,
        secondCurrency: String
    ): Single<CurrencyConverterEntity> {
        return remoteDataSource.getCurrencyConverter(
            baseCurrency.plus(",").plus(secondCurrency)
        )
            .flatMap {
                Single.just(it.mapToList().mapToCurrencyConverter())
            }
    }



    override fun getCurrencyListWithDate(
        baseCurrency: String,
        secondCurrency: String,
        lastDate: String,
        currentDate: String
    ): Single<CurrenciesDate> {
        return remoteDataSource.getCurrencyListWithDate(
            baseCurrency,
            secondCurrency,
            compact,
            lastDate,
            currentDate,
        ).flatMap {
            Single.just(it.mapToListDate().mapToCurrencyListDomain())
        }
    }
}