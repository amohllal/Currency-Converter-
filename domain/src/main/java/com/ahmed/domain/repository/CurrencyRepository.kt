package com.ahmed.domain.repository

import com.ahmed.domain.entities.CountriesEntities
import com.ahmed.domain.entities.CurrencyEntities
import io.reactivex.Single

interface CurrencyRepository {


    fun getAllCurrency() : Single<List<CurrencyEntities>>

}