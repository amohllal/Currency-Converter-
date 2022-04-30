package com.ahmed.domain.usecase

import com.ahmed.domain.repository.CountriesRepository
import com.ahmed.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(private val countryRepo: CountriesRepository) {

    fun invoke() = countryRepo.getAllCountries()
}