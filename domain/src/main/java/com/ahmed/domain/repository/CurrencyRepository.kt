package com.ahmed.domain.repository

import com.ahmed.domain.entities.CountriesEntities
import com.ahmed.domain.entities.CurrenciesDate
import com.ahmed.domain.entities.CurrencyConverterEntity
import com.ahmed.domain.entities.CurrencyEntities
import io.reactivex.Single

interface CurrencyRepository {

    fun getAllCurrency() : Single<List<CurrencyEntities>> ?=null
    fun getCurrencyListFromLocalStorage(): Single<List<CurrencyEntities>> ?= null

    fun getAllCountries() : Single<List<CountriesEntities>> ?= null
    fun getCountryListFromLocalStorage(): Single<List<CountriesEntities>> ?= null

    fun getConverterCurrency(baseCurrency:String,secondCurrency : String) : Single<CurrencyConverterEntity>? = null
    fun getCurrencyWithDate(baseCurrency:String,secondCurrency : String,date : String) : Single<CurrencyConverterEntity> ?= null

    fun getCurrencyListWithDate(baseCurrency:String,secondCurrency : String,lastDate : String,currentDate : String) : Single<CurrenciesDate> ?= null
}