package com.ahmed.domain.entities

data class CurrenciesDate(
    val baseCurrencyEntity : BaseCurrenciesEntity,
    val secondCurrencyEntity : SecondCurrenciesEntity
)

data class BaseCurrenciesEntity(val date : List<String>, val currency : List<String>)
data class SecondCurrenciesEntity(val date : List<String>, val currency : List<String>)