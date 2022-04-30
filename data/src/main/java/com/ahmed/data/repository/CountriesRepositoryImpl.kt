package com.ahmed.data.repository

import com.ahmed.data.core.getAPIKEY
import com.ahmed.data.mapper.mapToCountriesDomain
import com.ahmed.data.mapper.mapToDomain
import com.ahmed.data.mapper.mapToList
import com.ahmed.data.remote.CurrencyAPI
import com.ahmed.domain.entities.CountriesEntities
import com.ahmed.domain.repository.CountriesRepository
import io.reactivex.Single
import javax.inject.Inject

class CountriesRepositoryImpl @Inject constructor(private val api : CurrencyAPI):CountriesRepository {
    override fun getAllCountries(): Single<List<CountriesEntities>> {
        return api.getCountries(getAPIKEY())
            .flatMap {
                Single.just(it.mapToList().mapToCountriesDomain())
            }
    }
}