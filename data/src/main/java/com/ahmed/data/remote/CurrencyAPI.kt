package com.ahmed.data.remote

import com.ahmed.data.core.getAPIKEY
import com.ahmed.data.model.CountriesModel
import com.ahmed.data.model.CurrencyModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyAPI {

    @GET("currencies")
    fun getCurrency(@Query("apiKey") apiKey: String = getAPIKEY()): Single<CurrencyModel>

    @GET("countries")
    fun getCountries(@Query("apiKey") apiKey: String = getAPIKEY()): Single<CountriesModel>

    @GET("convert")
    fun getCurrencyConvert(
        @Query("q") currencySelector: String,
        @Query("compact") ultra: String = "ultra",
        @Query("apiKey") apiKey: String = getAPIKEY()
    ): Single<Map<String, String>>

    @GET("convert")
    fun getCurrencyConvertWithDate(
        @Query("q") currencySelector: String,
        @Query("compact") ultra : String = "ultra",
        @Query("date") date: String,
        @Query("apiKey") apiKey: String = getAPIKEY()
    ): Single<Map<String, Map<String, Double>>>
}