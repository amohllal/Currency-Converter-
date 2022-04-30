package com.ahmed.domain.repository

import com.ahmed.domain.entities.CountriesEntities
import io.reactivex.Single

interface CountriesRepository {
    fun getAllCountries() : Single<List<CountriesEntities>>

}