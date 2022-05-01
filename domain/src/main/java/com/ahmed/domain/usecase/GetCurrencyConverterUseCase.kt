package com.ahmed.domain.usecase

import com.ahmed.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetCurrencyConverterUseCase @Inject constructor(private val currencyRepo : CurrencyRepository) {
    fun invoke(baseCurrency:String,secondCurrency : String) = currencyRepo.getConverterCurrency(baseCurrency, secondCurrency)
}