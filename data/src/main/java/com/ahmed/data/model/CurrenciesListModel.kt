package com.ahmed.data.model

data class CurrenciesListModel(
    val baseCurrency : BaseCurrencies,
    val secondCurrency : SecondCurrencies
)

data class BaseCurrencies(val date : List<String>,val currency : List<String>)
data class SecondCurrencies(val date : List<String>,val currency : List<String>)
