package com.ahmed.data.repository

import android.util.Log
import com.ahmed.data.core.getAPIKEY
import com.ahmed.data.mapper.*
import com.ahmed.data.model.Currencies
import com.ahmed.data.remote.CurrencyAPI
import com.ahmed.domain.entities.CountriesEntities
import com.ahmed.domain.entities.CurrencyConverterEntity
import com.ahmed.domain.entities.CurrencyEntities
import com.ahmed.domain.repository.CurrencyRepository
import io.reactivex.Single
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(private val api: CurrencyAPI) :
    CurrencyRepository {

    override fun getAllCurrency(): Single<List<CurrencyEntities>> {
        return api.getCurrency()
            .flatMap {
                Single.just(it.mapToList().mapToDomain())
            }
    }


    override fun getAllCountries(): Single<List<CountriesEntities>> {
        return api.getCountries()
            .flatMap {
                Single.just(it.mapToList().mapToCountriesDomain())
            }
    }


    override fun getConverterCurrency(
        baseCurrency: String,
        secondCurrency: String
    ): Single<CurrencyConverterEntity> {
        return api.getCurrencyConvert(
            baseCurrency.plus(",").plus(secondCurrency))
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
            baseCurrency.plus(",").plus(secondCurrency),"",date
        ).flatMap {
            Single.just(it.mapToListWithDate().mapToCurrencyConverter())
        }
    }
}