package com.ahmed.domain.usecase

import com.ahmed.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetCurrencyUseCase @Inject constructor(private val currencyRepo : CurrencyRepository) {
    fun invoke() = currencyRepo.getAllCurrency()
    fun getCurrencyFromDatabase() = currencyRepo.getCurrencyListFromLocalStorage()
}