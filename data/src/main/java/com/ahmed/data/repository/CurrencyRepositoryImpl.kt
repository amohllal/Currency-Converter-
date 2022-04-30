package com.ahmed.data.repository

import com.ahmed.data.core.getAPIKEY
import com.ahmed.data.mapper.mapToDomain
import com.ahmed.data.mapper.mapToList
import com.ahmed.data.model.Currencies
import com.ahmed.data.remote.CurrencyAPI
import com.ahmed.domain.entities.CurrencyEntities
import com.ahmed.domain.repository.CurrencyRepository
import io.reactivex.Single
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(private val api : CurrencyAPI) : CurrencyRepository {
    override fun getAllCurrency(): Single<List<CurrencyEntities>> {

        return api.getCurrency(getAPIKEY())
            .flatMap {
            Single.just(it.mapToList().mapToDomain())
        }
    }
}