package com.ahmed.data.mapper

import com.ahmed.data.model.*
import com.ahmed.domain.entities.CountriesEntities
import com.ahmed.domain.entities.CurrencyConverterEntity
import com.ahmed.domain.entities.CurrencyEntities

fun CurrencyModel.mapToList() = this.results.toList().map { hmap ->
    Currencies(
        currencyName = hmap.second.currencyName,
        currencySymbol = hmap.second.currencySymbol,
        id = hmap.second.id
    )
}


fun List<Currencies>.mapToDomain() = this.map {
    CurrencyEntities(currencyName = it.currencyName, currencySymbol = it.currencySymbol, id = it.id)
}

fun CountriesModel.mapToList() = this.results.toList().map { hmap ->
    Countries(
        currencyName = hmap.second.currencyName,
        currencySymbol = hmap.second.currencySymbol,
        id = hmap.second.id,
        alpha3 = hmap.second.alpha3,
        currencyId = hmap.second.currencyId,
        name = hmap.second.name
    )
}


fun List<Countries>.mapToCountriesDomain() = this.map {
    CountriesEntities(
        currencyName = it.currencyName,
        currencySymbol = it.currencySymbol,
        id = it.id,
        alpha3 = it.alpha3,
        currencyId = it.currencyId,
        name = it.name
    )
}

fun Map<String,String>.mapToList() : Currency{
    return Currency(
        baseCurrency = this.entries.first().value,
        secondCurrency = this.entries.last().value
    )
}


fun Currency.mapToCurrencyConverter() =  CurrencyConverterEntity(
    baseCurrency = this.baseCurrency,
    secondCurrency = this.secondCurrency
)
fun Map<String,Map<String,Double>>.mapToListWithDate():Currency{
    val baseCurrency = this.entries.first().value.entries.first().value.toString()
    val secondCurrency =this.entries.last().value.entries.first().value.toString()
    return Currency(baseCurrency = baseCurrency,secondCurrency)
}





