package com.ahmed.domain.usecase

import com.ahmed.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetCurrencyWithDateUseCase @Inject constructor(private val currencyRepo : CurrencyRepository) {
    fun invoke(baseCurrency:String,secondCurrency : String,date : String) = currencyRepo.getCurrencyWithDate(baseCurrency, secondCurrency, date)
}