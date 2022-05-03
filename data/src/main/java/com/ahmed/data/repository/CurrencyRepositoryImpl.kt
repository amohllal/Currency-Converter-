package com.ahmed.data.repository

import android.util.Log
import com.ahmed.data.core.compact
import com.ahmed.data.local.CountriesDAO
import com.ahmed.data.local.CurrencyDAO
import com.ahmed.data.mapper.*
import com.ahmed.data.remote.CurrencyAPI
import com.ahmed.domain.entities.CountriesEntities
import com.ahmed.domain.entities.CurrenciesDate
import com.ahmed.domain.entities.CurrencyConverterEntity
import com.ahmed.domain.entities.CurrencyEntities
import com.ahmed.domain.repository.CurrencyRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val api: CurrencyAPI,
    private val currencyDAO: CurrencyDAO,
    private val countryDAO: CountriesDAO
) : CurrencyRepository {

    override fun getAllCurrency(): Single<List<CurrencyEntities>> {
        return api.getCurrency()
            .flatMap {
                Completable.fromAction {
                    currencyDAO.saveListOfCurrency(it.mapToList().mapToEntity())
                }.andThen(Single.just(it.mapToList().mapToDomain()))
            }.onErrorResumeNext {
                currencyDAO.getCurrencyFromDatabase().map {
                    it.mapToRemoteResponse().mapToDomain()
                }
            }

    }

    override fun getCurrencyListFromLocalStorage(): Single<List<CurrencyEntities>> {
        return currencyDAO.getCurrencyFromDatabase().map {
            it.mapToRemoteResponse().mapToDomain()
        }
    }

    override fun getAllCountries(): Single<List<CountriesEntities>> {
        return api.getCountries()
            .flatMap {
                Completable.fromAction {
                    countryDAO.saveListOfCountries(it.mapToList().mapToCountryEntity())
                }.andThen(Single.just(it.mapToList().mapToCountriesDomain()))

            }.onErrorResumeNext {
                countryDAO.getCountryFromDatabase().map {
                    it.mapToCountryRemoteResponse().mapToCountriesDomain()
                }
            }
    }

    override fun getCountryListFromLocalStorage(): Single<List<CountriesEntities>> {
        return countryDAO.getCountryFromDatabase().map {
            it.mapToCountryRemoteResponse().mapToCountriesDomain()
        }
    }

    override fun getConverterCurrency(
        baseCurrency: String,
        secondCurrency: String
    ): Single<CurrencyConverterEntity> {
        return api.getCurrencyConvert(
            baseCurrency.plus(",").plus(secondCurrency)
        )
            .flatMap {
                Single.just(it.mapToList().mapToCurrencyConverter())
            }
    }


    override fun getCurrencyWithDate(
        baseCurrency: String,
        secondCurrency: String,
        date: String
    ): Single<CurrencyConverterEntity> {
        return api.getCurrencyConvertWithDate(
            baseCurrency.plus(",").plus(secondCurrency), compact, date
        ).flatMap {
            Single.just(it.mapToListWithDate().mapToCurrencyConverter())
        }
    }

    override fun getCurrencyListWithDate(
        baseCurrency: String,
        secondCurrency: String,
        lastDate: String,
        currentDate: String
    ): Single<CurrenciesDate> {
        return api.getCurrencyListWithDate(
            baseCurrency.plus(",").plus(secondCurrency),
            compact,
            lastDate,
            currentDate
        ).flatMap {
            Single.just(it.mapToListDate().mapToCurrencyListDomain())
        }
    }
}