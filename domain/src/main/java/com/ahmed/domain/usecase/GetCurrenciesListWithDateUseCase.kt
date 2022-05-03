package com.ahmed.domain.usecase

import com.ahmed.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetCurrenciesListWithDateUseCase @Inject constructor(private val currencyRepo : CurrencyRepository) {
    fun invoke(baseCurrency:String, secondCurrency : String, lastDate : String, currentDate : String) = currencyRepo.getCurrencyListWithDate(baseCurrency, secondCurrency, lastDate,currentDate)
}