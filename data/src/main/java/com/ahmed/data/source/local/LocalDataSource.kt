package com.ahmed.data.source.local

import com.ahmed.data.local.CountriesDAO
import com.ahmed.data.local.CurrencyDAO
import com.ahmed.data.model.local.CountriesLocalEntity
import com.ahmed.data.model.local.CurrencyLocalEntity
import io.reactivex.Single
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val currencyDAO: CurrencyDAO,
    private val countriesDAO: CountriesDAO
) {
    fun saveCurrencies(list: List<CurrencyLocalEntity>) {
        currencyDAO.saveListOfCurrency(list)
    }

    fun getCurrenciesFromDataBase(): Single<List<CurrencyLocalEntity>> {
        return currencyDAO.getCurrencyFromDatabase()
    }

    fun saveCountries(list: List<CountriesLocalEntity>) {
        countriesDAO.saveListOfCountries(list)
    }

    fun getCountriesFromDataBase(): Single<List<CountriesLocalEntity>> {
        return countriesDAO.getCountryFromDatabase()
    }
}