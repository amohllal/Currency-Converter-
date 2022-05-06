package com.ahmed.data.source.remote

import com.ahmed.data.core.compact
import com.ahmed.data.model.CountriesModel
import com.ahmed.data.model.CurrencyModel
import com.ahmed.data.remote.CurrencyAPI
import io.reactivex.Single
import retrofit2.http.Query
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val currencyAPI: CurrencyAPI) {
    fun getCurrency(): Single<CurrencyModel> {
        return currencyAPI.getCurrency()
    }

    fun getCountries(): Single<CountriesModel> {
        return currencyAPI.getCountries()
    }

    fun getCurrencyConverter(
        currencySelector: String,
    ): Single<Map<String, String>> {
        return currencyAPI.getCurrencyConvert(currencySelector)
    }

    fun getCurrencyListWithDate(
        baseCurrency: String,
        secondCurrency: String,
        ultra : String,
        lastDate: String,
        currentDate: String
    ): Single<Map<String, Map<String, Double>>> {
        return currencyAPI.getCurrencyListWithDate(
            baseCurrency.plus(",").plus(secondCurrency),
            ultra,
            lastDate,
            currentDate
        )
    }

}