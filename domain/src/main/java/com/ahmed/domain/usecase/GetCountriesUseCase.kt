package com.ahmed.domain.usecase

import com.ahmed.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(private val currencyRepo : CurrencyRepository) {

    fun invoke() = currencyRepo.getAllCountries()
    fun getCountryFromDatabase() = currencyRepo.getCountryListFromLocalStorage()
}