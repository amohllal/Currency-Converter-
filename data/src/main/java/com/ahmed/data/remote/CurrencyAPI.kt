package com.ahmed.data.remote

import com.ahmed.data.model.CurrencyModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyAPI {

    @GET("currencies")
    fun getCurrency(@Query("apiKey") apiKey: String) : Single<CurrencyModel>
}